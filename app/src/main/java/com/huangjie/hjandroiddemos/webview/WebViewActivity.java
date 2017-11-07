package com.huangjie.hjandroiddemos.webview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;

public class WebViewActivity extends BaseActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        tv = (TextView) findViewById(R.id.tv);
        String text = "<p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\">&nbsp; &nbsp; &nbsp;虽然窗帘作为软饰，不如家具占的空间大，但对房间整体影响非常大，窗帘材质颜色选得不好，与家具墙壁色彩不搭，与整个装修风格不和谐，整个空间看起来就会非常尴尬，也会影响到后期入住的心情。</span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><br/></span></p><p style=\\\"text-align: center;\\\"><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><img src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023332775084.png\\\" _src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023332775084.png\\\"/></span></p><p><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\">&nbsp;</span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><span>1</span>、比墙面的颜色深一点点，可以选择跟墙面颜色相近，或者稍微深几个度的颜色。比如墙面是浅浅的米色，窗帘选同色系，整个空间柔和又温暖。如下图</span></p><p></p><p style=\\\"text-align: center;\\\"><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><img src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333025316.png\\\" _src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333025316.png\\\"/></span></p><p><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><br/></span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><span>2</span>、最简单的方法就是干脆跟墙面同色，统统用白色，视觉也不是不可以。不过白色窗帘实在太不耐脏，可以放在卧室，接触的人会比客厅少一些。</span></p><p></p><p style=\\\"text-align: center;\\\"><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><img src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333187974.png\\\" _src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333187974.png\\\"/></span></p><p><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><br/></span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><span>3</span>、如果房间是以灰调为主，这时候搭配银灰色的，看上去整个空间会显得很高级。不会破坏整体感觉。</span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><br/></span></p><p style=\\\"text-align: center;\\\"><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><img src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333438140.png\\\" _src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333438140.png\\\"/></span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><br/></span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><span>4</span>、有不少欧式或美式风的房间，整体色调偏深，或者家里颜色比较多，这时候再“比墙面颜色深一点点”，估计就会太暗沉了。所以，设计大神教我们的第二个技巧是——跟着靠垫的颜色或者沙发颜色走。这样这个空间既明亮，又和谐。</span></p><p></p><p style=\\\"text-align: center;\\\"><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><img src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333557363.png\\\" _src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333557363.png\\\"/></span></p><p><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><br/></span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><span>5</span>、撞色搭配法，这是目前年轻人偏爱的方式，选择与空间冷暖相对的颜色。也就是空间冷系，窗帘选择暖色系色彩，给空间增添一份活力。</span></p><p><span style=\\\"font-family:微软雅黑, Microsoft YaHei\\\"><span></span></span></p><p style=\\\"text-align: center;\\\"><span style=\\\"undefinedfont-family:微软雅黑, Microsoft YaHei\\\"><img src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333651857.png\\\" _src=\\\"http://www.yizhuangw.cn/Public/js/umeditor-utf8-php/php/upload/20170810/15023333651857.png\\\"/></span></p><p><br/></p>\\t";
        tv.setText(Html.fromHtml(text));
    }



    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        activity.startActivity(intent);
    }
}
