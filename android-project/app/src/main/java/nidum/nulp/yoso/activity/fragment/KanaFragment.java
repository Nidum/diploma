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
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;
import nidum.nulp.yoso_project.R;

import static nidum.nulp.yoso.utill.IntentHolder.ARG_HIEROGLYPH_TYPE;
import static nidum.nulp.yoso.utill.IntentHolder.ARG_ORDER;

public class KanaFragment extends Fragment {

    public static final String ARG_KANA_CURRENT = "kana_current";
    public static final String ARG_KANA_OTHER = "kana_other";
    public static final String ARG_KANA_READING = "kana_reading";
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
        if(isHiragana) {
            args.putString(ARG_HIEROGLYPH_TYPE, EntityType.HIRAGANA.name());
        } else {
            args.putString(ARG_HIEROGLYPH_TYPE, EntityType.KATAKANA.name());
        }
        args.putInt(ARG_ORDER, kana.getOrder());
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
                intent.putExtra(ARG_ORDER, arguments.getInt(ARG_ORDER));
                intent.putExtra(ARG_HIEROGLYPH_TYPE, arguments.getString(ARG_HIEROGLYPH_TYPE));
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
        String name = arguments.getString(ARG_HIEROGLYPH_TYPE);
        EntityType type = EntityType.valueOf(name);
        if(type == EntityType.HIRAGANA) {
            this.arguments.putString(ARG_HIEROGLYPH_TYPE, EntityType.KATAKANA.name());
        } else {
            this.arguments.putString(ARG_HIEROGLYPH_TYPE, EntityType.HIRAGANA.name());
        }
    }
}
