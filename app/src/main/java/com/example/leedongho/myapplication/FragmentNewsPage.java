package com.example.leedongho.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentNewsPage extends Fragment {

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> title1 = new ArrayList<>();
    ArrayList<String> link = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    BaseAdapter adapter;
    ListView listView;
    String target;


    public FragmentNewsPage() {


    }

    @Override
    public void onStart() {
        super.onStart();
        target = "http://www.dementianews.co.kr/";
        new JsoupAsyncTask(title, 0).execute(target + "news/articleList.html?page=1&total=778&sc_section_code=S1N1&view_type=sm", "div[class=list-titles]");
        new JsoupAsyncTask(link, 1).execute(target + "news/articleList.html?page=1&total=778&sc_section_code=S1N1&view_type=sm", "a[class=line-height-3-2x]");
//        new JsoupAsyncTask(content, 0).execute(target + "news/articleList.html?page=1&total=778&sc_section_code=S1N1&view_type=sm", "a[class=line-height-3-2x]");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 플레그먼트 인플레이트
        View view = inflater.inflate(R.layout.activity_fragment_news, container, false);
        Log.v("title:", "" + title);
        adapter = new NewAdapter(getActivity(), title1);
        listView = (ListView) view.findViewById(R.id.listview1);
        listView.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getActivity(),item,Toast.LENGTH_SHORT).show();
            }
        });
        listView.setVerticalScrollBarEnabled(false);
        return view;
    }
/*    public void setListViewHeightBasedOnChildren(ListView view){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter==null){
            return;
        }
        int totalHeigh = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),View.MeasureSpec.AT_MOST);
        for(int i = 0 ; i<listAdapter.getCount();i++){
            View listItem = listAdapter.getView(i,null,listView);
            listItem.measure(desiredWidth,View.MeasureSpec.UNSPECIFIED);
            totalHeigh += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeigh + (listView.getDividerHeight()*(listAdapter.getCount()-1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }*/


    private class JsoupAsyncTask extends AsyncTask<String, Void, String> {
        ArrayList arrayList;
        int linkresult;
        View view;



        JsoupAsyncTask(ArrayList arrayList, int linkresult) {
            this.arrayList = arrayList;
            this.linkresult = linkresult;
        }

        @Override
        protected String doInBackground(String... params) {

            String URL = params[0];
            String E1 = params[1];
            String result = "";

            try {
                Document document = Jsoup.connect(URL).get();
                Elements elements = document.select(E1);

                for (Element element : elements) {
                    if (linkresult == 0) {
                        arrayList.add(element.text());
                    } else {
                        arrayList.add(element.attr("href"));
                    }
                }

                return result;
            } catch (IOException e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            title1.removeAll(title1);

            adapter.notifyDataSetChanged();
            for(int i =0 ; i<10;i++){
                title1.add(title.get(i));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.v("title:", "" + title);
        title1.removeAll(title1);

        adapter.notifyDataSetChanged();

    }
}

