package com.powerrich.office.oa;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//        System.out.println(StringUtil.checkUserName("345612_3abc"));
//        loadGeneric(String.class);
        Text<String> t = new Text<>("a", "b");
//        /^/
        System.out.println(checkIdCard("asdf"));

    }
    public static boolean checkIdCard(String idCard) {
        String regex = "[ a-z0-9]$";
        return Pattern.matches(regex, idCard);
    }

//     <T> DrawableTypeRequest<T> loadGeneric(Class<T> modelClass) {
//         return optionsApplier.apply(
//                 new DrawableTypeRequest<T>(modelClass, streamModelLoader, fileDescriptorModelLoader, context,
//                         glide, requestTracker, lifecycle, optionsApplier));
//    }


    class Text<HAHA> {
        private HAHA a;
        private HAHA b;

        public Text(HAHA a, HAHA b) {
            this.a = a;
            this.b = b;
        }
    }
}