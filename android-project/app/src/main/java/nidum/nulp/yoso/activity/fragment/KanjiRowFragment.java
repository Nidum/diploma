package nidum.nulp.yoso.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nidum.nulp.yoso.activity.HieroglyphAnimationActivity;
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.utill.ResourceLoader;
import nidum.nulp.yoso_project.R;

import static nidum.nulp.yoso.utill.IntentHolder.ARG_HIEROGLYPH_TYPE;
import static nidum.nulp.yoso.utill.IntentHolder.ARG_NORYOKU_LEVEL;
import static nidum.nulp.yoso.utill.IntentHolder.ARG_ORDER;

public class KanjiRowFragment extends Fragment {

    public static final String ARG_READING = "kana_current";
    public static final String ARG_MEANING = "kana_other";
    public static final String ARG_LEVEL_IMAGE = "lvl_img";
    public static final String ARG_HIEROGLYPH = "kana_reading";
    public static final String ARG_IMPORTANT = "important";
    public static final String ARG_KANJI_ORDER = "kanji_order";

    private ImageView masteringImageView;
    private TextView meaningTextView;
    private TextView kanjiTextView;
    private TextView readingTextView;
    private TextView importantTextView;

    private Context appContext;
    private Bundle arguments;

    public static KanjiRowFragment newInstance(int lvlImg, String reading, String meaning, String hieroglyph,
                                               EntityType entityType, boolean important, int order, NoryokuLevel noryokuLevel) {
        KanjiRowFragment fragment = new KanjiRowFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEVEL_IMAGE, lvlImg);
        args.putString(ARG_READING, reading);
        args.putString(ARG_MEANING, meaning);
        args.putString(ARG_HIEROGLYPH, hieroglyph);
        args.putBoolean(ARG_IMPORTANT, important);
        args.putInt(ARG_ORDER, order);
        args.putString(ARG_HIEROGLYPH_TYPE, entityType.name());
        if (entityType.equals(EntityType.KANJI)) {
            args.putString(ARG_NORYOKU_LEVEL, noryokuLevel.name());
        }
        fragment.setArguments(args);
        return fragment;
    }

    public KanjiRowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_kanji_row, container, false);

        masteringImageView = fragmentView.findViewById(R.id.level_image_view);
        meaningTextView = fragmentView.findViewById(R.id.meaning_text_view);
        kanjiTextView = fragmentView.findViewById(R.id.kanji_text_view);
        readingTextView = fragmentView.findViewById(R.id.reading_text_view);
        importantTextView = fragmentView.findViewById(R.id.important_text_view);

        View layout = fragmentView.findViewById(R.id.kanji_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = arguments.getString(ARG_HIEROGLYPH_TYPE);
                if(type.equals(EntityType.KANJI.name())) {
                    Intent intent = new Intent(getActivity(), HieroglyphAnimationActivity.class);
                    intent.putExtra(ARG_HIEROGLYPH_TYPE, type);
                    intent.putExtra(ARG_HIEROGLYPH, readingTextView.getText());
                    intent.putExtra(ARG_KANJI_ORDER, arguments.getInt(ARG_KANJI_ORDER));
                    intent.putExtra(ARG_ORDER, arguments.getInt(ARG_ORDER));
                    intent.putExtra(ARG_NORYOKU_LEVEL, arguments.getString(ARG_NORYOKU_LEVEL));

                    startActivity(intent);
                }
            }
        });

        masteringImageView.setImageResource(arguments.getInt(ARG_LEVEL_IMAGE));
        meaningTextView.setText(arguments.getString(ARG_MEANING));
        kanjiTextView.setText(arguments.getString(ARG_HIEROGLYPH));
        kanjiTextView.setTypeface(ResourceLoader.KANJI_FONT);

        readingTextView.setText(arguments.getString(ARG_READING));
        if (!arguments.getBoolean(ARG_IMPORTANT)) {
            importantTextView.setVisibility(View.INVISIBLE);
        }

        return fragmentView;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }
}
