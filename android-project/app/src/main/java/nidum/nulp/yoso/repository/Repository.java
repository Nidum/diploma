package nidum.nulp.yoso.repository;

import java.util.List;

import nidum.nulp.yoso.entity.Hieroglyph;

public interface Repository<T extends Hieroglyph> {
    List<T> getAll();

    void updateStudyData(T hieroglyph);
}
