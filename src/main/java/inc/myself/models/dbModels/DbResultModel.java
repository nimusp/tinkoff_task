package inc.myself.models.dbModels;

import javax.persistence.*;

@Entity
@Table(name = "SEARCH_RESULT")
public class DbResultModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    /**
     * код выполнения про- граммы
     */
    @Column(name = "CODE")
    private String code;

    /**
     * число, переданное на вход
     */
    @Column(name = "NUMBER")
    private Integer number;

    /**
     * имена файлов, в которых удалось найти число
     */
    @Column(name = "FILENAMES")
    private String fileName;

    /**
     * описание ошибки, в случае её возникновения
     */
    @Column(name = "ERROR")
    private String error;

    public DbResultModel() {  }

    public DbResultModel(String code, Integer number, String fileName, String error) {
        this.code = code;
        this.number = number;
        this.fileName = fileName;
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DbResultModel{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", number=" + number +
                ", fileName='" + fileName + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
