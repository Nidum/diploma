package nidum.nulp.yoso.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nidum.nulp.yoso.utill.ResourceLoader;
import nidum.nulp.yoso_project.R;

public class KanjiRowFragment extends Fragment {

    public static final String ARG_READING = "kana_current";
    public static final String ARG_MEANING = "kana_other";
    public static final String ARG_LEVEL_IMAGE = "lvl_img";
    public static final String ARG_KANJI = "kana_reading";
    public static final String ARG_IMPORTANT = "important";

    private ImageView masteringImageView;
    private TextView meaningTextView;
    private TextView kanjiTextView;
    private TextView readingTextView;
    private TextView importantTextView;

    private Context appContext;
    private Bundle arguments;

    public static KanjiRowFragment newInstance(int lvlImg, String reading, String meaning, String kanji, boolean important) {
        KanjiRowFragment fragment = new KanjiRowFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEVEL_IMAGE, lvlImg);
        args.putString(ARG_READING, reading);
        args.putString(ARG_MEANING, meaning);
        args.putString(ARG_KANJI, kanji);
        args.putBoolean(ARG_IMPORTANT, important);

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

//        View layout = fragmentView.findViewById(R.id.kana_layout);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), HieroglyphAnimationActivity.class);
//                intent.putExtra(ARG_READING, meaningTextView.getText());
//                intent.putExtra(ARG_KANJI, readingTextView.getText());
//                startActivity(intent);
//            }
//        });

        masteringImageView.setImageResource(arguments.getInt(ARG_LEVEL_IMAGE));
        meaningTextView.setText(arguments.getString(ARG_MEANING));
        kanjiTextView.setText(arguments.getString(ARG_KANJI));

        //Typeface customFont = Typeface.createFromAsset(appContext.getAssets(),  "fonts/Radicals Font.ttf");
        kanjiTextView.setTypeface(ResourceLoader.KANJI_FONT);

        readingTextView.setText(arguments.getString(ARG_READING));
        if(!arguments.getBoolean(ARG_IMPORTANT)) {
            importantTextView.setVisibility(View.INVISIBLE);
        }

        return fragmentView;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }
}
