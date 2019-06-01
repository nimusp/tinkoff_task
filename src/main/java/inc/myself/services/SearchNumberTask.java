package inc.myself.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.util.concurrent.RecursiveTask;

public class SearchNumberTask extends RecursiveTask<String> {

    private final File file;
    private final int numberToSearch;

    /**
     * поиск заданного числа в файле
     * @param file файл для поиска
     * @param numberToSearch число для поиска
     */
    public SearchNumberTask(File file, int numberToSearch) {
        this.file = file;
        this.numberToSearch = numberToSearch;
    }

    /**
     * поиск заканчивается при первом совпадении
     * @return название файла в случае, если заданное число присутствует файле
     * или null, если число не найдено
     */
    @Override
    protected String compute() {
        String result = null;
        try (BufferedReader in = Files.newBufferedReader(file.toPath())) {
            StreamTokenizer reader = new StreamTokenizer(in);
            while (reader.nextToken() != StreamTokenizer.TT_EOF) {
                int number = (int)reader.nval;
                if (numberToSearch == number) {
                    result = file.getName();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
