package inc.myself.services;

import inc.myself.models.Result;
import inc.myself.models.ResultCode;
import inc.myself.models.dbModels.DbResultModel;
import inc.myself.repos.SearchResultRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;

@Service
public class SearchNumberServiceImpl implements SearchNumberService {

    @Value("${dirWithFiles}")
    private String fileDirectory;

    @Autowired
    private SearchResultRepository repository;

    private final FilenameFilter filter = (dir, name) -> name.toLowerCase().endsWith(".txt");
    private final Logger logger = LogManager.getLogger(SearchNumberServiceImpl.class);

    @Override
    @NonNull
    public Result findNumber(@Nullable Integer number) {
        if (number == null) {
            logger.info("invalid parameter: " + number);
            Result wrongParametersResult = new Result();
            wrongParametersResult.setCode(ResultCode.ERROR.getDescription());
            wrongParametersResult.setError("Invalid parameters");
            saveToDatabase(number, wrongParametersResult);
            return wrongParametersResult;
        }

        File[] files = new File(fileDirectory).listFiles(filter);
        List<String> fileWithNumberList = parseFilesForNumber(files, number);

        Result result = new Result();
        result.getFileNames().addAll(fileWithNumberList);

        if (fileWithNumberList.isEmpty()) {
            logger.info("number " + number + " not found in " + Arrays.toString(files));
            result.setCode(ResultCode.NOT_FOUND.getDescription());
            result.setError("Number not found");
        } else {
            logger.info("number " + number + " founded in " + fileWithNumberList.size() + " files: " + fileWithNumberList);
            result.setCode(ResultCode.OK.getDescription());
            result.setError(null);
        }

        saveToDatabase(number, result);
        return result;
    }

    @NonNull
    private List<String> parseFilesForNumber(@NonNull File[] files, @NonNull Integer number) {
        List<String> fileList = new ArrayList<>();
        List<ForkJoinTask<String>> taskList = new ArrayList<>();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        for (File file : files) {
            if (file.isFile()) {
                ForkJoinTask<String> submittedTask = pool.submit(new SearchNumberTask(file, number));
                taskList.add(submittedTask);
            }
        }
        for (ForkJoinTask<String> task : taskList) {
            fileList.add(task.join());
        }

        return fileList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void saveToDatabase(@Nullable Integer number, @NonNull Result result) {
        String formattedFilenameList = result.getFileNames().stream()
                .collect(Collectors.joining(", "));

        DbResultModel dbModel = new DbResultModel(
                result.getCode(),
                number,
                formattedFilenameList,
                result.getError()
        );
        repository.save(dbModel);
        logger.info("result " + dbModel.toString() + " successfully saved in db");
    }
}
