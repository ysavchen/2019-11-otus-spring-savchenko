package com.mycompany.hw_l04_spring_boot.dao;

import com.mycompany.hw_l04_spring_boot.app.AppLocale;
import com.mycompany.hw_l04_spring_boot.domain.Answer;
import com.mycompany.hw_l04_spring_boot.domain.Question;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class StorageImpl implements Storage {

    private final AppLocale appLocale;

    @Override
    public List<Question> getQuestions() {
        String questionsPath = "/questions_" + appLocale.getLocale() + ".csv";

        return getRecords(questionsPath).stream()
                .map(record -> {
                    int id = parseInt(record.get("id"));
                    return new Question(
                            id,
                            record.get("question"),
                            List.of(record.get("options").split(",")),
                            new Answer(id, record.get("correctAnswer"))
                    );
                })
                .collect(toList());
    }

    @SneakyThrows
    private List<CSVRecord> getRecords(String path) {
        @Cleanup InputStream is = this.getClass().getResourceAsStream(path);
        Reader reader = new InputStreamReader(is);
        return CSVFormat.INFORMIX_UNLOAD
                .withFirstRecordAsHeader()
                .parse(reader)
                .getRecords();
    }
}
