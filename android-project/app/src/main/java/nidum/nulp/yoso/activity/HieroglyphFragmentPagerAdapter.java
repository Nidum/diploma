package nidum.nulp.yoso.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nidum.nulp.yoso.activity.fragment.FlashCardFragment;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.repository.KanaRepository;

public class HieroglyphFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Kana> kanaList;
    private EntityType entityType;

    public HieroglyphFragmentPagerAdapter(FragmentManager fm, KanaRepository kanaRepository, EntityType entityType) {
        super(fm);
        kanaList = kanaRepository.getAllKana();
        Collections.sort(kanaList, new Comparator<Kana>() {
            @Override
            public int compare(Kana o1, Kana o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        this.entityType = entityType;
    }

    @Override
    public Fragment getItem(int position) {
        Kana kana = kanaList.get(position);

        FlashCardFragment flashCardFragment = FlashCardFragment.newInstance(kana, entityType);
        return flashCardFragment;
    }


    @Override
    public int getCount() {
        return kanaList.size();
    }

    public List<Kana> getKanaList() {
        return kanaList;
    }
}