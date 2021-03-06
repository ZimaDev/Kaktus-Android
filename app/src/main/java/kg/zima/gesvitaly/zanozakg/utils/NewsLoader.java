package kg.zima.gesvitaly.zanozakg.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.OptionalInt;
import com.annimon.stream.Stream;
import com.muddzdev.styleabletoast.StyleableToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.fragments.NewsFragment;
import kg.zima.gesvitaly.zanozakg.models.Comment;
import kg.zima.gesvitaly.zanozakg.models.Media;
import kg.zima.gesvitaly.zanozakg.models.NewsTag;
import kg.zima.gesvitaly.zanozakg.models.Topic;
import kg.zima.gesvitaly.zanozakg.models.TopicPreview;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.BulletListItemVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.CardVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.CarouselVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.CommentVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.CommentsTitleVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.ContentHtmlVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.DateAndAuthorsVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.IFrameVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.ImageVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.PieceOfArticle;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.QuizVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.QuoteInCommentVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.QuoteVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.SpoilerVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.SubtitleVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TagsCloudVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TextVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TitleVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TopicPreviewVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.VrezVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.YouTubeVideoVM;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ZanozaApiClient;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ZanozaApiService;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ResponseForLoadPieceOfArticle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kg.zima.gesvitaly.zanozakg.CarouselFlags.SHOW_AUTHORS;
import static kg.zima.gesvitaly.zanozakg.CarouselFlags.SHOW_DESCRIPTION;

public class NewsLoader {
    public static void loadArticle(int id, NewsFragment fragment, Context context) {
        ZanozaApiService zanozaApiService = ZanozaApiClient.getClient().create(ZanozaApiService.class);
        Call<ResponseForLoadPieceOfArticle> call = zanozaApiService.loadPieceOfArticle(id);

        call.enqueue(new Callback<ResponseForLoadPieceOfArticle>() {
            @Override
            public void onResponse(@NonNull Call<ResponseForLoadPieceOfArticle> call, @NonNull Response<ResponseForLoadPieceOfArticle> response) {
                if (response.body().getSuccess().equals("false")) {
                    StyleableToast
                        .makeText(context, context.getString(R.string.data_retrieve_error), R.style.StyleableToast)
                        .show();
                    fragment.showEmpty();
                    return;
                }

                ArrayList<NewsTag> mainNewsTagsList = response.body().getNewsTags();
                Topic topic = response.body().getTopic();
                ArrayList<PieceOfArticle> piecesOfArticle = buildPiecesOfArticleList(topic, mainNewsTagsList, false);

                fragment.setPiecesOfArticle(piecesOfArticle);
                fragment.setTopicName(topic.getName());
                fragment.setTopicUrl(topic.getUrl());

                fragment.trackNewsView();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseForLoadPieceOfArticle> call, @NonNull Throwable t) {
                if (context != null) {
                    StyleableToast
                        .makeText(context, context.getString(R.string.data_retrieve_error), R.style.StyleableToast)
                        .show();
                }
                fragment.showEmpty();
            }
        });
    }

    public static ArrayList<PieceOfArticle> buildPiecesOfArticleList(Topic topic, @Nullable ArrayList<NewsTag> mainNewsTagsList, boolean isVrez) {
        ArrayList<PieceOfArticle> piecesOfArticle = new ArrayList<>();

        // ???????? ?????? ???? ????????, ???? ???????????????????? ??????????????????
        if (!isVrez) {
            String title = topic.getName();
            piecesOfArticle.add(new TitleVM(title));

            if (mainNewsTagsList != null) {
                String dateAndAuthor = "";
                String authorsString;
                String dateSting;
                int date = topic.getDate();
                ArrayList<String> authors = ZanozaUtils.getAuthors(topic.getTagsIds(), mainNewsTagsList);
                if (date != 0) {
                    dateSting = Utils.getTimePassedString(date);
                    dateAndAuthor += dateSting;
                    if (!authors.isEmpty()) dateAndAuthor += "\n";
                }
                if (!authors.isEmpty()) {
                    authorsString = authors.toString().replace("[", "").replace("]", "");
                    dateAndAuthor += authorsString;
                }
                if (!dateAndAuthor.isEmpty())
                    piecesOfArticle.add(new DateAndAuthorsVM(dateAndAuthor));
            }

            //TODO: ???????????????? ??????????
            // ???????? ?????????? ?????????? ???????????????????? ?? ???????????????? ?? ?????? ????????????????, ?????????????????????? ?????? ??????????
            ArrayList<Media> medias = Stream.of(topic.getMedia())
                    .filter(media -> media.isShowInCarousel() && (media.getType() == 1 || media.getType() == 2))
                    .collect(Collectors.toCollection(ArrayList::new));
            /*ArrayList<String> imgURLs = Stream.of(topic.getMedia())
                    .filter(media -> media.isShowInCarousel() && (media.getType() == 1 || media.getType() == 2 || media.getType() == 7))
                    .map(Media::getMediumImgUrl)
                    .collect(Collectors.toCollection(ArrayList::new));*/

            if (!medias.isEmpty()) {
//                String imgURLsString = imgURLs.toString();
                piecesOfArticle.add(new CarouselVM(medias, SHOW_AUTHORS));
            }

        }

        ArrayList<PieceOfArticle> body = buildBody(topic);
        piecesOfArticle.addAll(body);


        // ???????? ?????? ???? ????????, ???? ???????????????????? ???????? ?? ??????????????????????
        if (!isVrez) {
            ArrayList<NewsTag> tagsToDisplay = ZanozaUtils.getTagsToDisplay(topic.getTagsIds(), mainNewsTagsList);
            if (tagsToDisplay != null && !tagsToDisplay.isEmpty())
                piecesOfArticle.add(new TagsCloudVM(tagsToDisplay));

            ArrayList<Comment> comments = topic.getComments();
            if (comments != null && !comments.isEmpty()) {
                piecesOfArticle.add(new CommentsTitleVM("??????????????????????"));
                piecesOfArticle.addAll(Stream.of(comments).map(CommentVM::new).collect(Collectors.toList()));
            }
        }

        return piecesOfArticle;
    }

    // ?????????????????? ?? ???????????? piecesOfNews ??????, ?????????? ??????????????????, ???????????????? ?? ????????????????????????
    public static ArrayList<PieceOfArticle> buildBody(Topic topic) {
        ArrayList<PieceOfArticle> body = new ArrayList<>();

        Document doc = Jsoup.parse(topic.getText());
        ArrayList<Element> elements = doc.body().children();

        // ???????????????? ???????? ?????????????????? ??????????????????
        int cardNum = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).hasClass("topic-card")) {
                elements.get(i).prependChild((new Element("div"))
                        .addClass("topic-card-index")
                        .text(Utils.padLeft(String.valueOf(++cardNum), 2, '0')));
            }
            elements.set(i, elements.get(i));
        }

        // ???????????? ??????????????????
        LinkedHashSet<Element> lhs = new LinkedHashSet<>();
        lhs.addAll(elements);
        elements.clear();
        elements.addAll(lhs);

        ArrayList<Element> filteredElements = new ArrayList<>();
        Stream.of(elements)
                .filter(e -> !e.parent().hasClass("content_html"))
                .forEach(filteredElements::add);
        ArrayList<Element> finalElements = new ArrayList<>();
        Stream.of(filteredElements).forEach(e -> {
            Element element = e.clone();
            boolean isElementCleared = false;
            boolean isStyleAdded = false;
            // ???????? ?????????? ???????????????? ???????? ?????????????????????????? ??????????
            if (Stream.of(e.children()).anyMatch(child -> child.hasAttr("src") && child.attr("src").contains("https://www.facebook.com/plugins/video.php"))) {
                for (Element child : e.children()) {
                    if (child.hasAttr("src") && child.attr("src").contains("https://www.facebook.com/plugins/video.php")) {
                        // ???????? ???? ???????? ????????????, ???? ?????????? ?????????????? ?????? ???? content_html, ?????????? ?????????????????? ????????????
                        if (!isElementCleared) {
                            element.empty();
                            isElementCleared = true;
                        }
                        String newSrc = child.attr("src").replaceFirst("&width=\\d+", "");
                        child.attributes().put("src", newSrc);
                        child.attributes().put("width", "100%");
                        child.attributes().put("height", "100%");
                        child.addClass("facebook_video");
                        // ???????????????????? ??????????, ?????????? ?????????? ???? ???????????????? ???????????????????????? ??????????
                        if (!isStyleAdded) {
                            element.appendChild(ZanozaUtils.create16x9Style());
                            isStyleAdded = true;
                        }
                        // ???????????????????? ?????????? ???? ???????????????? ?? facebook_video_parent
                        Element facebookVideoParent = new Element(Tag.valueOf("div"), "");
                        facebookVideoParent.addClass("facebook_video_parent");
                        facebookVideoParent.appendChild(child);
                        element.appendChild(facebookVideoParent);
                    }
                    // ?????????? ???????????????? ?????????????? ?????? ??????????????????
                    else element.appendChild(child);
                }
            }

/*            // ?????? ?????????????????????????? ????????????; ???? ????????????????
            Stream.of(element.children())
                    .filter(child -> child.hasAttr("src") && child.attr("src").contains("https://www.facebook.com/plugins/post.php"))
                    .forEach(child -> {
                        String newSrc = child.attr("src").replaceFirst("&width=\\d+", "&width=auto");
                        child.attributes().put("src", newSrc);
                        child.attributes().put("id", "facebook_iframe");
                        child.attributes().remove("width");
                        child.attributes().remove("height");
                        element.appendChild(ZanozaUtils.iframeResizer());
                    });*/

            // ???????????????? ?????????????? ?? ???????????????????????????? ???????????? ?????? ??????????????????????
            finalElements.add(element);
        });

        for (Element element : finalElements) {
            addPieceOfArticle(element, body, topic);
        }

        return body;
    }

    private static boolean addPieceOfArticle(Element element, ArrayList<PieceOfArticle> piecesOfArticle, Topic topic) {
        if (element.hasClass("media")) {
            Map<String, String> elemAttributes = element.dataset();
            // ???????? ?????????????????? ?????????? - ????????????????
            String type = elemAttributes.get("type");
            if (("img".equals(type) || ("pics".equals(type)) || ("gif".equals(type))) && topic != null) {
                // ???? ???????????????????? id ?????????????????? ?????????? ???????????? ???????????? ?? ???????????? ????????????
                Optional<Media> media = Stream.of(topic.getMedia())
                        .filter(m -> m.getId() == Integer.valueOf(elemAttributes.get("id")))
                        .findFirst();
                // ???????? ?? ?????????? ???????????????? ?????????????? ???????? ???????? ????????????????
                if (media.isPresent()) {
                    piecesOfArticle.add(new ImageVM(
                            media.get().getType(),
                            media.get().getSmallImgUrl(),
                            media.get().getMediumImgUrl(),
                            media.get().getBigImgUrl(),
                            media.get().getAuthors()
                    ));

                    // ???????????????? ???????????????? ????????????????, ???????? ????????
                    String imgDesc = media.get().getDescription();
                    if (!imgDesc.isEmpty())
                        piecesOfArticle.add(new TextVM("<i>" + imgDesc + "</i>"));
                    // TODO: ???????????????????? ?????????? ImageVH
                    return true;
                }
            }
        /*} else if (element.tag() == Tag.valueOf("img")) {
            String src = element.attr("src");
            if (!src.isEmpty()) {
                piecesOfArticle.add(new ImageVM(src));
                return true;
            }*/
        } else if (element.hasClass("bnr")) {
            Optional<Element> videoDiv = Stream.of(element.children())
                    .filter(child -> child.hasClass("video_parent"))
                    .findFirst();
            if (videoDiv.isPresent()) {
                OptionalInt isAdded = Stream.of(videoDiv.get().children())
                        .filter(child -> child.tag() == Tag.valueOf("iframe") && child.hasAttr("src"))
                        .findFirst()
                        .mapToInt(iframe -> {
                            String iframeSrc = iframe.getElementsByAttribute("src").first().attributes().get("src");
                            if (iframeSrc.contains("youtube")) {
                                String youtubeVideoId = iframeSrc.replaceAll("^.*/([^/]*)/?$", "$1");
                                piecesOfArticle.add(new YouTubeVideoVM(youtubeVideoId));
                                return 1;
                            }
                            return 0;
                        });
                if (isAdded.isPresent() && isAdded.getAsInt() == 1) return true;
            }

        } else if (element.tag() == Tag.valueOf("iframe")) {
            piecesOfArticle.add(new IFrameVM(element.outerHtml()));
            return true;
        } else if (element.hasClass("content_html")) {
            // ?????????? ?????? ????????????
            element.appendChild(ZanozaUtils.createTableStyle());
            piecesOfArticle.add(new ContentHtmlVM(element.outerHtml()));
            return true;
        } else if (element.tag() == Tag.valueOf("ol") || element.tag() == Tag.valueOf("ul")) {
            piecesOfArticle
                    .addAll(Stream.of(element.children())
                            .filter(child -> child.tag() == Tag.valueOf("li"))
                            .map(child -> new BulletListItemVM(child.outerHtml()))
                            .collect(Collectors.toList()));
            return true;
        } else if (element.tag() == Tag.valueOf("li")) {
            piecesOfArticle.add(new BulletListItemVM(element.ownText()));
            return true;
        } else if (element.hasClass("quiz") && element.hasAttr("data-id")) {
            piecesOfArticle.add(new QuizVM(Integer.parseInt(element.attr("data-id"))));
            return true;
        } else if (element.hasClass("vrez")) {
            if (topic == null) return false;
            if (element.hasClass("vrez-big")) {
                for (Element e : element.children()) {
                    addPieceOfArticle(e, piecesOfArticle, topic);
                }
                return true;
            }
            if (!Stream.of(element.children()).anyMatch(ch -> ch.hasClass("quiz"))) {
                Topic vrezTopic = new Topic(topic);
                vrezTopic.setText(element.html());
                piecesOfArticle.add(new VrezVM(vrezTopic));
                return true;
            }
        } else if (element.tag() == Tag.valueOf("h2")) {
            piecesOfArticle.add(new SubtitleVM(element.text()));
            return true;
        } else if (element.tag() == Tag.valueOf("blockquote")) {
            if (element.hasClass("bb-quote")) {
                if (!element.children().isEmpty()) {
                    StringBuilder text = new StringBuilder();
                    int size = element.children().size();
                    boolean isFirstParagraph = true;
                    for (int i = 0; i < size; i++) {
                        Element child = element.child(i);
                        if (topic != null) {
                            if (child.tag() == Tag.valueOf("p")) {
                                if (isFirstParagraph) isFirstParagraph = false;
                                    // ?????????? ????????????????, ?????????? ??????????????, ???????????????? ?????????????? ????????????
                                else text.append('\n');
                                text.append(child.html());
                            }
                        }
                    }
                    piecesOfArticle.add(new QuoteVM(text.toString()));
                    return true;
                } else if (!element.text().isEmpty()) {
                    piecesOfArticle.add(new QuoteVM(element.ownText()));
                    return true;
                }
            } else if (!Stream.of(element.children()).anyMatch(ch -> ch.hasClass("quiz"))) {
                if (topic == null) return false;
                piecesOfArticle.add(new QuoteInCommentVM(element.html(), topic));
                return true;
            }
        } else if (element.hasClass("bb_news")) {
            ArrayList<TopicPreview> topicPreviews = topic.getTopicPreviews();
            if (topicPreviews == null) return false;
            Attributes elemAttributes = element.attributes();
            int topicId = Integer.parseInt(elemAttributes.get("data-id"));
            Optional<TopicPreview> topicPreviewOptional = Stream.of(topicPreviews)
                    .filter(tp -> tp.getId() == topicId)
                    .findFirst();
            if (!topicPreviewOptional.isPresent()) return false;
            TopicPreview topicPreview = topicPreviewOptional.get();
            piecesOfArticle.add(new TopicPreviewVM.Builder()
                    .name(topicPreview.getName())
                    .url(topicPreview.getUrl())
                    .imgUrls(topicPreview.getImgUrls())
                    .commentsCount(topicPreview.getCommentsCount())
                    .build());
            return true;
        } else if (element.hasClass("topic-gallery")) {
            if (topic.getMedia() == null) return false;
            String[] s = element.attr("data-media-ids").split(",");
            ArrayList<Integer> ids = Stream.of(s)
                    .map(Integer::valueOf)
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Media> medias = Stream.of(topic.getMedia())
                    .filter(media -> ids.contains(media.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (medias.isEmpty()) return false;
            // ???????? ?? ???????????????? ???????????? ???????? ????????????????, ???? ???????????? ???????????????? ???????????????? ???????????? ????????????????
            if (medias.size() == 1) {
                Media media = medias.get(0);
                piecesOfArticle.add(new ImageVM(
                        media.getType(),
                        media.getSmallImgUrl(),
                        media.getMediumImgUrl(),
                        media.getBigImgUrl(),
                        media.getAuthors()
                ));
            } else piecesOfArticle.add(new CarouselVM(medias, SHOW_DESCRIPTION));
            return true;
        } else if (element.hasClass("topic-card")) {
            String cardNum = Stream.of(element.children())
                    .filter(ch -> ch.hasClass("topic-card-index"))
                    .findFirst().get().ownText();
            // ???????? ???????????? - ???????????? ????????????????, ???? ???????????? ???? ??????????
            if (piecesOfArticle.get(piecesOfArticle.size() - 1) instanceof CardVM) {
                piecesOfArticle.add(CardVM.builder()
                        .text(element.html())
                        .topic(topic)
                        .cardNumber(cardNum)
                        .needTopMargin(false)
                        .build());
            } else {
                piecesOfArticle.add(CardVM.builder()
                        .text(element.html())
                        .topic(topic)
                        .cardNumber(cardNum)
                        .needTopMargin(true)
                        .build());
            }
            return true;
        } else if (element.hasClass("bb-spoiler")) {
            if (topic == null) return false;
            Topic spoilerTopic = new Topic(topic);
            spoilerTopic.setText("");
            String title = "??????????????";
            for (Element child : element.children()) {
                if (child.hasClass("bb-spoiler-title")) title = child.html();
                else if (child.hasClass("bb-spoiler-content")) spoilerTopic.setText(child.html());
            }
            piecesOfArticle.add(new SpoilerVM(title, spoilerTopic));
            return true;
        } else if (element.tag() == Tag.valueOf("p")) {
            if (!element.childNodes().isEmpty()) {
                String text = "";
                int size = element.childNodeSize();
                for (int i = 0; i < size; i++) {
                    Node childNode = element.childNode(i);
                    boolean isNodeAdded = false;
                    // ???????? ???????????????? ???????? - ?????????????? - ???????????????? ????????????????
                    if (childNode instanceof Element && topic != null) {
                        isNodeAdded = addPieceOfArticle((Element) childNode, piecesOfArticle, topic);
                    }
                    // ???????? ???????? ???? ????????????????, ???? ?????????????????? ?????? ??????????
                    if (!isNodeAdded) {
                        text += childNode;
                    }
                }
                text = text.trim();
                if (!text.isEmpty()) piecesOfArticle.add(new TextVM(text));
                return true;
            } else {
                // ???????????????? ?????????? ?????????????? ?? ????????????
                if (!element.text().isEmpty())
                    piecesOfArticle.add(new TextVM(element.ownText().trim()));
                return true;
            }
        }
        return false;
    }

}
