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
import nidum.nulp.yoso.repository.KanaRepository;

public class KanaFragmentPagerAdapter extends FragmentPagerAdapter {

    private FloatingActionButton fab;
    private List<Kana> kanaList;
    private boolean isHiragana;

    public KanaFragmentPagerAdapter(FragmentManager fm, FloatingActionButton fab, KanaRepository kanaRepository, boolean isHiragana) {
        super(fm);
        this.fab = fab;
        kanaList = kanaRepository.getAllKana();
        Collections.sort(kanaList, new Comparator<Kana>() {
            @Override
            public int compare(Kana o1, Kana o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        this.isHiragana = isHiragana;
    }

    @Override
    public Fragment getItem(int position) {
        Kana kana = kanaList.get(position);

        FlashCardFragment flashCardFragment = FlashCardFragment.newInstance(kana, isHiragana);
        return flashCardFragment;
    }


    @Override
    public int getCount() {
        return kanaList.size();
    }

    public void setHiragana(boolean hiragana) {
        isHiragana = hiragana;
    }
}