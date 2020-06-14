package com.holdbetter.dbperfectproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.holdbetter.dbperfectproject.gson.GoogleBookData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class GoogleBooksViewModel extends ViewModel
{
    private MutableLiveData<GoogleBookData> bookData;
    private MutableLiveData<String> loadingState = new MutableLiveData<>("not ready");
    private final URL URL = new URL("https://www.googleapis.com/books/v1/volumes?q=Гарри+inauthor:rowling&orderBy=relevance&langRestrict=ru&maxResults=8&key=AIzaSyDoXCEeshumVEN742nqRPVCOqBqt6n3crA");

    public GoogleBooksViewModel() throws MalformedURLException
    {
    }

    public void downloadBooks()
    {
        new Thread(() ->
        {
            try
            {
                downloadUrl(URL);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();
    }

    public LiveData<GoogleBookData> getBookData()
    {
        if (bookData == null)
        {
            bookData = new MutableLiveData<>();
        }
        return bookData;
    }

    public MutableLiveData<String> getLoadingState()
    {
        return loadingState;
    }

    // sample by https://github.com/android/connectivity-samples/tree/master/NetworkConnect
    private void downloadUrl(java.net.URL url) throws IOException
    {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        try
        {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            loadingState.postValue("downloading...");
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK)
            {
                throw new IOException("HTTP error code: " + responseCode);
            }
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            loadingState.postValue("downloading..");
            if (stream != null)
            {
                result = readStream(stream, 1024 * 200);
                loadingState.postValue("downloaded");
                bookData.postValue(new Gson().fromJson(result, GoogleBookData.class));
            }
        } finally
        {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null)
            {
                stream.close();
            }
            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }

    private String readStream(InputStream stream, int maxLength) throws IOException
    {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        // Create temporary buffer to hold Stream data with specified max length.
        char[] buffer = new char[maxLength];
        // Populate temporary buffer with Stream data.
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1)
        {
            numChars += readSize;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1)
        {
            // The stream was not empty.
            // Create String that is actual length of response body if actual length was less than
            // max length.
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }
}
