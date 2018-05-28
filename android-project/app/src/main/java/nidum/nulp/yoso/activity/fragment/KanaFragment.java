package nidum.nulp.yoso.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nidum.nulp.yoso.activity.HieroglyphAnimationActivity;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;
import nidum.nulp.yoso_project.R;

public class KanaFragment extends Fragment {

    public static final String ARG_KANA_CURRENT = "kana_current";
    public static final String ARG_KANA_OTHER = "kana_other";
    public static final String ARG_KANA_READING = "kana_reading";
    public static final String ARG_KANA_ORDER = "kana_order";
    public static final String IS_HIRAGANA = "is_hiragana";
    public static final String ARG_STUDY_LEVEL = "noryoku_level";

    private ImageView masteringImageView;
    private TextView currentKanaTextView;
    private TextView otherKanaTextView;
    private TextView readingTextView;

    private Bundle arguments;

    public static KanaFragment newInstance(Kana kana, boolean isHiragana) {
        KanaFragment fragment = new KanaFragment();
        Bundle args = new Bundle();
        if(isHiragana) {
            args.putString(ARG_KANA_CURRENT, kana.getHiragana());
            args.putString(ARG_KANA_OTHER, kana.getKatakana());
            args.putInt(ARG_STUDY_LEVEL, kana.getHiraganaStudyLevel().ordinal());
        } else {
            args.putString(ARG_KANA_CURRENT, kana.getKatakana());
            args.putString(ARG_KANA_OTHER, kana.getHiragana());
            args.putInt(ARG_STUDY_LEVEL, kana.getKatakanaStudyLevel().ordinal());
        }
        args.putString(ARG_KANA_READING, kana.getReading());
        args.putBoolean(IS_HIRAGANA, isHiragana);
        args.putInt(ARG_KANA_ORDER, kana.getOrder());
        fragment.setArguments(args);
        return fragment;
    }

    public KanaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_kana, container, false);
        masteringImageView = fragmentView.findViewById(R.id.level_image_view);
        currentKanaTextView = fragmentView.findViewById(R.id.current_kana_text_view);
        otherKanaTextView = fragmentView.findViewById(R.id.other_kana_text_view);
        readingTextView = fragmentView.findViewById(R.id.reading_text_view);

        View layout = fragmentView.findViewById(R.id.kana_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HieroglyphAnimationActivity.class);
                intent.putExtra(ARG_KANA_CURRENT, currentKanaTextView.getText());
                intent.putExtra(ARG_KANA_READING, readingTextView.getText());
                intent.putExtra(ARG_KANA_ORDER, arguments.getInt(ARG_KANA_ORDER));
                intent.putExtra(IS_HIRAGANA, arguments.getBoolean(IS_HIRAGANA));
                startActivity(intent);
            }
        });

        masteringImageView.setImageResource(StudyLevel.values()[arguments.getInt(ARG_STUDY_LEVEL)].getImageId());
        currentKanaTextView.setText(arguments.getString(ARG_KANA_CURRENT));
        otherKanaTextView.setText(arguments.getString(ARG_KANA_OTHER));
        readingTextView.setText(arguments.getString(ARG_KANA_READING));

        return fragmentView;
    }

    public void updateIsHiragana(){
        this.arguments.putBoolean(IS_HIRAGANA, !arguments.getBoolean(IS_HIRAGANA));
    }
}
