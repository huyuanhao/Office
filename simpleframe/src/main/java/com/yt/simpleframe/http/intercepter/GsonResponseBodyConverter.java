/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yt.simpleframe.http.intercepter;


import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String str = value.string();
        String startKey = "<ns1:out>";
        String endKey = "</ns1:out>";
        int indexStart = str.indexOf(startKey) + startKey.length();
        int indexEnd = str.indexOf(endKey);
        String newStr = str.substring(indexStart, indexEnd);
//        LogUtil.e("yuanhao",newStr);
        ResponseBody newValue = ResponseBody.create(MediaType.parse("application/json;charset=UTF-8"), newStr);
        JsonReader jsonReader = gson.newJsonReader(newValue.charStream());
        T result = null;
        try {
            result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        } finally {
            value.close();
            newValue.close();
        }
    }
}
