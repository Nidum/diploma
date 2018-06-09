package nidum.nulp.yoso.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import nidum.nulp.yoso_project.R;

public class InstructionActivity extends AppCompatActivity {

    Map<String, String> questions;
    Map<String, String> m;

    ExpandableListView elvMain;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        questions = new TreeMap<String, String>() {
        };
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.instructions)));
        try {
            while (inputStream.ready()) {
                String question = inputStream.readLine();
                String answer = inputStream.readLine();
                questions.put(question, answer);
            }
        } catch (IOException e){

        }

        ArrayList<Map<String, String>> groupData = new ArrayList<>();
        for (String group : questions.keySet()) {
            m = new HashMap<>();
            m.put("groupName", group);
            groupData.add(m);
        }

        String groupFrom[] = new String[]{"groupName"};
        int groupTo[] = new int[]{android.R.id.text1};

        ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<>();
        for (String answer : questions.keySet()) {
            ArrayList<Map<String, String>> children = new ArrayList<>();
            Map<String, String> attributeMap = new HashMap<>();
            attributeMap.put("answer", questions.get(answer));
            children.add(attributeMap);
            childData.add(children);
        }

        String childFrom[] = new String[]{"answer"};
        int childTo[] = new int[]{android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        elvMain = findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }
}
