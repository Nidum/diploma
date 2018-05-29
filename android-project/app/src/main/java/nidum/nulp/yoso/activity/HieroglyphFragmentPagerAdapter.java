package nidum.nulp.yoso.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nidum.nulp.yoso.activity.fragment.FlashCardFragment;
import nidum.nulp.yoso.entity.Hieroglyph;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso.repository.Repository;

public class HieroglyphFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<? extends Hieroglyph> dataList;
    private EntityType entityType;

    public HieroglyphFragmentPagerAdapter(FragmentManager fm, Repository<? extends Hieroglyph> repository, EntityType entityType) {
        super(fm);
        dataList = repository.getAll();
        this.entityType = entityType;
    }

    @Override
    public Fragment getItem(int position) {
        Hieroglyph item = dataList.get(position);

        FlashCardFragment flashCardFragment = FlashCardFragment.newInstance(item, entityType);
        return flashCardFragment;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    public List<?> getDataList() {
        return dataList;
    }
}