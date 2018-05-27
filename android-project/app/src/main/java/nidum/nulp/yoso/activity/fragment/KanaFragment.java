package nidum.nulp.yoso.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nidum.nulp.yoso.activity.KanaAnimationActivity;
import nidum.nulp.yoso_project.R;

public class KanaFragment extends Fragment {

    public static final String ARG_LEVEL_IMAGE = "lvl_img";
    public static final String ARG_KANA_CURRENT = "kana_current";
    public static final String ARG_KANA_OTHER = "kana_other";
    public static final String ARG_KANA_READING = "kana_reading";
    public static final String IS_KANA = "is_kana";

    private ImageView masteringImageView;
    private TextView currentKanaTextView;
    private TextView otherKanaTextView;
    private TextView readingTextView;

    private Bundle arguments;

    public static KanaFragment newInstance(int lvlImg, String kana1, String kana2, String kanaReading) {
        KanaFragment fragment = new KanaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEVEL_IMAGE, lvlImg);
        args.putString(ARG_KANA_CURRENT, kana1);
        args.putString(ARG_KANA_OTHER, kana2);
        args.putString(ARG_KANA_READING, kanaReading);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
                Intent intent = new Intent(getActivity(), KanaAnimationActivity.class);
                intent.putExtra(ARG_KANA_CURRENT, currentKanaTextView.getText());
                intent.putExtra(ARG_KANA_READING, readingTextView.getText());
                intent.putExtra(IS_KANA, true);
                startActivity(intent);
            }
        });

        masteringImageView.setImageResource(arguments.getInt(ARG_LEVEL_IMAGE));
        currentKanaTextView.setText(arguments.getString(ARG_KANA_CURRENT));
        otherKanaTextView.setText(arguments.getString(ARG_KANA_OTHER));
        readingTextView.setText(arguments.getString(ARG_KANA_READING));

        return fragmentView;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

}
