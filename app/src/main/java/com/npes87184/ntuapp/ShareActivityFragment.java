package com.npes87184.ntuapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daimajia.numberprogressbar.NumberProgressBar;

/**
 * Created by npes87184 on 2015/4/12.
 */
public class ShareActivityFragment extends Fragment {


    private View v;
    private WebView mWebView;
    public static ShareActivityFragment newInstance(int index) {
        ShareActivityFragment shareActivityFragment = new ShareActivityFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("ShareActivityFragment", index);
        shareActivityFragment.setArguments(args);

        return shareActivityFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_shareactivity, container, false);
        mWebView = (WebView) v.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                final NumberProgressBar bnp = (NumberProgressBar)v.findViewById(R.id.number_progress_bar);
                bnp.setProgressTextVisibility(NumberProgressBar.ProgressTextVisibility.Visible);
                bnp.setProgress(progress);
                if (progress == 100) {
                    bnp.setProgressTextVisibility(NumberProgressBar.ProgressTextVisibility.Invisible);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //設置點擊網頁裡面的鏈接還是在當前的webview裡跳轉
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, android.net.http.SslError error) {
                //設置webview處理https請求
                handler.proceed();
            }
        });

        mWebView.loadUrl("https://ntu-activities.herokuapp.com/activities/new");
        return v;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    mWebView.goBack();
                }break;
            }
        }
    };
}
