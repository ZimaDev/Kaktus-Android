package kg.zima.gesvitaly.zanozakg.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.fragments.FullscreenImageFragment;
import kg.zima.gesvitaly.zanozakg.fragments.NewsFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.TagsFragment;
import kg.zima.gesvitaly.zanozakg.models.NewsTag;

import static androidx.core.content.ContextCompat.startActivity;

public abstract class ZanozaUtils {
    public static ArrayList<String> getAuthors(ArrayList<Integer> newsTags, ArrayList<NewsTag> mainNewsTagsList) {
        // Получаю имена авторов, чьи id есть в новости (newsTags), из тегов новостей (mainNewsTagsList)
        return Stream.of(mainNewsTagsList)
                .filter(t -> t.getCatId() == 10 && newsTags.contains(t.getId()))
                .map(NewsTag::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<NewsTag> getTagsToDisplay(ArrayList<Integer> newsTags, ArrayList<NewsTag> mainNewsTagsList) {
        ArrayList<Integer> tagsNotToDisplayCatIds = new ArrayList<Integer>() {
            {
                add(2);
                add(5);
                add(6);
                add(10);
                add(11);
                add(12);
                add(13);
                add(14);
            }
        };
        return Stream.of(mainNewsTagsList)
                .filter(t -> newsTags.contains(t.getId()) && !tagsNotToDisplayCatIds.contains(t.getCatId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Element create16x9Style() {
        Element style = new Element(Tag.valueOf("style"), "");
        style.append(".facebook_video_parent {\n" +
                "  width: 100%;\n" +
                "  display: inline-block;\n" +
                "  position: relative;\n" +
                "}\n" +
                ".facebook_video_parent:after {\n" +
                "  padding-top: 56.25%;\n" +
                "  /* 16:9 ratio */\n" +
                "  display: block;\n" +
                "  content: '';\n" +
                "}\n" +
                ".facebook_video {\n" +
                "  position: absolute;\n" +
                "  top: 0;\n" +
                "  bottom: 0;\n" +
                "  right: 0;\n" +
                "  left: 0;\n" +
                "  /* fill parent */\n" +
                "  background-image: url(\"http://i.imgur.com/AMmM26D.png\");\n" +
                "  /* let's see it! */\n" +
                "  color: white;\n" +
                "}");
        return style;
    }

    public static Element createTableStyle() {
        Element style = new Element(Tag.valueOf("style"), "");
        style.append("table {\n" +
                "    width: 100%;\n" +
                "    border-spacing: 0px;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "th,\n" +
                "td {\n" +
                "    padding: 10px 11px;\n" +
                "    border-right: 2px solid #f1f1f1;\n" +
                "    border-top: 2px solid #f1f1f1;\n" +
                "text-align: left;\n" +
                "vertical-align: middle;\n" +
                "}\n" +
                "th {\n" +
                "    font-weight: bold;\n" +
                "\tbackground-color: #f1f1f1;\n" +
                "\tcolor: #E14D3E;\n" +
                "}\n" +
                "tr th:last-child,\n" +
                "tr td:last-child{\n" +
                "\tborder-right:none;\n" +
                "}\n" +
                "tr th:last-child,\n" +
                "tr td:last-child{\n" +
                "\tborder-right:none;\n" +
                "}\n" +
                "tr:nth-child(2n+1) td{\n" +
                "    background-color: #F9F9F9;\n" +
                "}");
        return style;
    }

    public static Element createResponsiveHeightStyle() {
        Element style = new Element(Tag.valueOf("style"), "");
        style.append("<script>\n" +
                "  function resizeIframe(obj) {\n" +
                "    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';\n" +
                "  }\n" +
                "</script>");
        return style;
    }

    /**
     * Если ссылка ведёт на статью Занозы, то открывает экран со статьёй. Если ссылка ведёт на изображение
     * с Занозы, то открывает его в полный экран. В противном случае если открывает в ChromeCustomTabs
     * (если поддерживаются) или в браузере
     * @param url Ссылка
     * @param fragment Текущий фрагмент
     */
    public static void handleLink(String url, Fragment fragment) {
        handleLink(url, fragment.getContext(), fragment.getFragmentManager());
    }

    /**
     * Если ссылка ведёт на статью Занозы, то открывает экран со статьёй. Если ссылка ведёт на изображение
     * с Занозы, то открывает его в полный экран. В противном случае если открывает в ChromeCustomTabs
     * (если поддерживаются) или в браузере
     * @param url Ссылка
     * @param activity Текущая активити
     */
    public static void handleLink(String url, AppCompatActivity activity) {
        handleLink(url, activity, activity.getSupportFragmentManager());
    }

    private static void handleLink(String url, Context context, FragmentManager fragmentManager) {
        if (url.contains("kaktus.media/doc")) {
            String id = url.replaceAll("^.*?doc/([\\d]*).*$", "$1");
            openArticle(Integer.valueOf(id), fragmentManager);
        } else if ((url.contains("data.kaktus.media") || url.contains("class='zanoza_pic'"))) {
            FullscreenImageFragment fullscreenImageFragment = new FullscreenImageFragment();
            fullscreenImageFragment.setSrc(url);
            // При повороте экрана не разрушает фрагмент
            fullscreenImageFragment.setRetainInstance(true);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContent, fullscreenImageFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (url.contains("kaktus.media/?lable")) {
            String idStr = url.replaceAll("^.*?lable=([\\S]*).*$", "$1");
            String[] labelsStr = idStr.split(",");
            int[] labels = new int[labelsStr.length];
            for (int i = 0; i < labels.length; i++) {
                labels[i] = Integer.parseInt(labelsStr[i]);
            }
            openTagsFragment(fragmentManager, labels);

        } else if (url.contains("https://kaktus.media/") || url.contains("http://kaktus.media/")) {
            String idStr = url.replaceAll("^.*?media/([\\d]*).*$", "$1");
//            String idStr = url.substring(18);
            try {
                int id = Integer.valueOf(idStr);
                openArticle(id, fragmentManager);
            } catch (NumberFormatException e) {
                openInBrowser(context, url);
            }
        } else {
            openInBrowser(context, url);
        }
    }

    private static void openArticle(int id, FragmentManager fragmentManager) {
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setNewsId(id);
        fragmentManager
                .beginTransaction()
                .replace(R.id.flContent, newsFragment)
                .addToBackStack(null)
                .commit();
    }

    private static void openInBrowser(Context context, String url) {
        if (Utils.isChromeCustomTabsSupported(context)) {
            new CustomTabsIntent.Builder()
                    .addDefaultShareMenuItem()
                    .build()
                    .launchUrl(context, Uri.parse(url));
        }
        else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(context, browserIntent, null);
        }
    }

    private static void openTagsFragment(FragmentManager fragmentManager, int[] labels) {
        TagsFragment tagsFragment = TagsFragment.newInstance(labels);
        fragmentManager
                .beginTransaction()
                .replace(R.id.flContent, tagsFragment)
                .addToBackStack(null)
                .commit();
    }
}
