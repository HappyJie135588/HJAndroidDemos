package com.huangjie.hjandroiddemos.live;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveListActivity extends BaseActivity {

    @BindView(R.id.rv_live)
    RecyclerView rv_live;
    LiveAdapter mAdapter;
    List<LiveEntity> liveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        rv_live.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LiveAdapter();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveActivity.actionStart(LiveListActivity.this, (LiveEntity) adapter.getItem(position));
            }
        });
        rv_live.setAdapter(mAdapter);
    }

    private void initData() {
        liveList = new ArrayList<>();
//        LiveEntity liveEntity01 = new LiveEntity("凤凰中文超清", "http://223.110.245.139:80/PLTV/3/224/3221226977/index.m3u8");
        LiveEntity liveEntity01 = new LiveEntity("凤凰中文超清", "http://117.144.248.49/HDhnws.m3u8?authCode=07110409322147352675&amp;stbId=006001FF0018120000060019F0D49A1&amp;Contentid=6837496099179515295&amp;mos=jbjhhzstsl&amp;livemode=1&amp;channel-id=wasusyt");
        LiveEntity liveEntity02 = new LiveEntity("凤凰中文超清", "http://183.251.61.197/PLTV/88888888/224/3221225900/index.m3u8");
        LiveEntity liveEntity03 = new LiveEntity("凤凰中文超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/fhchinese/dujuejiami.m3u8");
        LiveEntity liveEntity04 = new LiveEntity("凤凰中文超清", "http://183.251.61.207/PLTV/88888888/224/3221225900/index.m3u8");
        LiveEntity liveEntity05 = new LiveEntity("凤凰资讯超清", "http://183.251.61.207/PLTV/88888888/224/3221225901/index.m3u8");
        LiveEntity liveEntity06 = new LiveEntity("凤凰资讯超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/fhzixun/dujuejiami.m3u8");
        LiveEntity liveEntity07 = new LiveEntity("凤凰资讯超清", "http://183.207.249.34/PLTV/3/224/3221226980/index.m3u8");
        LiveEntity liveEntity08 = new LiveEntity("凤凰资讯超清", "http://223.110.245.139:80/PLTV/3/224/3221226980/index.m3u8");
        LiveEntity liveEntity09 = new LiveEntity("凤凰香港超清", "http://223.110.245.139:80/PLTV/3/224/3221226975/index.m3u8");
        LiveEntity liveEntity10 = new LiveEntity("凤凰香港超清", "http://183.207.249.35/PLTV/3/224/3221226975/index.m3u8");
        LiveEntity liveEntity11 = new LiveEntity("TVB翡翠台超清（亚洲版）", "http://acm.gg/jade.m3u8");
        LiveEntity liveEntity12 = new LiveEntity("新传媒8频道", "http://tglch8h-i.akamaihd.net/hls/live/566549/ctv/02.m3u8");
        LiveEntity liveEntity13 = new LiveEntity("新视觉HD", "http://222.83.46.35/PLTV/88888888/224/3221226338/10000100000000060000000001106406_0.smil");
        LiveEntity liveEntity14 = new LiveEntity("TVB新闻", "http://live.alicdn.com/live/inews.m3u8");
        LiveEntity liveEntity15 = new LiveEntity("TVB星河", "http://qqpull2.inke.cn/live/hellotv-tvbxh.m3u8");
        LiveEntity liveEntity16 = new LiveEntity("TVB剧集", "http://qqpull2.inke.cn/live/hellotv-tvbjj.m3u8");
        LiveEntity liveEntity17 = new LiveEntity("CHC高清电影 ", "http://zzcs1.ott.henancatv.com/mlive/172_30_100_78_/cdn_proxy/os_ott_henancatv_com/live/CHC-HD/3.m3u8");
        LiveEntity liveEntity18 = new LiveEntity("CHC家庭影院 ", "http://zzcs1.ott.henancatv.com/mlive/172_30_100_78_/cdn_proxy/os_ott_henancatv_com/live/CHC-JT/3.m3u8");
        LiveEntity liveEntity19 = new LiveEntity("CHC动作电影 ", "http://zzcs1.ott.henancatv.com/mlive/172_30_100_78_/cdn_proxy/os_ott_henancatv_com/live/CHC-DZ/3.m3u8");
        LiveEntity liveEntity20 = new LiveEntity("华丽台", "http://acm.gg/bili.m3u8?id=4139853");
        LiveEntity liveEntity21 = new LiveEntity("东方Knews24新闻高清", " http://hls.live.kksmg.com/live/live24h/playlist.m3u8");
        LiveEntity liveEntity22 = new LiveEntity("香港卫视", "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8");
        LiveEntity liveEntity23 = new LiveEntity("香港卫视", "rtmp://live.hkstv.hk.lxdns.com/live/hks");
        LiveEntity liveEntity24 = new LiveEntity("香港HKS", "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8");
        LiveEntity liveEntity25 = new LiveEntity("东森新闻HD", "http://edge01p.friday.tw/live/fet003/chunklist.m3u");
        LiveEntity liveEntity26 = new LiveEntity("英国天空新闻台", " http://skydvn-nowtv-atv-prod.skydvn.com/atv/skynews/1404/live/04.m3u8");
        LiveEntity liveEntity27 = new LiveEntity("香港耀才财经", "rtmp://202.69.69.180:443/webcast/bshdlive-pc");
        LiveEntity liveEntity28 = new LiveEntity("香港耀才财经", "http://202.69.67.66:443/webcast/bshdlive-pc/playlist.m3u8");
        LiveEntity liveEntity29 = new LiveEntity("香港耀才财经", "http://202.69.67.66:443/webcast/bshdlive-mobile/playlist.m3u8");
        LiveEntity liveEntity30 = new LiveEntity("香港耀才财经", "rtmp://fc_video.bsgroup.com.hk:443/webcast/bshdlive-pc");
        LiveEntity liveEntity31 = new LiveEntity("香港耀才财经", "rtmp://202.69.69.180:443/webcast/bshdlive-mobile");
        LiveEntity liveEntity32 = new LiveEntity("美国中文台", "http://media3.sinovision.net:1935/live/livestream/playlist.m3u8");
        LiveEntity liveEntity33 = new LiveEntity("新唐人亚太", "http://61.216.177.73/sta/ch1764015.m3u8");
        LiveEntity liveEntity34 = new LiveEntity("澳卫澳门", " http://live4.tdm.com.mo:80/ch1/_definst_//ch1.live/playlist.m3u8");
        LiveEntity liveEntity35 = new LiveEntity("澳视澳门", "http://live4.tdm.com.mo:80/ch1/_definst_//ch1.live/playlist.m3u8");
        LiveEntity liveEntity36 = new LiveEntity("澳视澳门", "rtmp://live4.tdm.com.mo:80/tv/ch1.live");
        LiveEntity liveEntity37 = new LiveEntity("澳视澳门", "rtmp://live3.tdm.com.mo:1935/tv/ch1.live");
        LiveEntity liveEntity38 = new LiveEntity("澳亚卫视", "http://ktv044.cdnak.ds.kylintv.net/nlds/kylin/mastv/as/live/mastv_4.m3u8");
        LiveEntity liveEntity39 = new LiveEntity("澳亚卫视", "http://live.mastvnet.com/n1rtlHG/500/live.m3u8");
        LiveEntity liveEntity40 = new LiveEntity("民视", "http://61.216.177.73/sta/ch1764025.m3u8");
        LiveEntity liveEntity41 = new LiveEntity("民视新闻HD", "http://61.216.177.73/sta/ch1764026.m3u8");
        LiveEntity liveEntity42 = new LiveEntity("民视新闻HD", "http://edge01p.friday.tw/live/fet002/chunklist.m3u");
        LiveEntity liveEntity43 = new LiveEntity("壹新闻", "http://d2e6xlgy8sg8ji.cloudfront.net/liveedge/eratv3/chunklist.m3u8");
        LiveEntity liveEntity44 = new LiveEntity("壹新闻", "http://d2e6xlgy8sg8ji.cloudfront.net/liveedge/eratv2/chunklist.m3u8");
        LiveEntity liveEntity45 = new LiveEntity("壹新闻", "http://d2e6xlgy8sg8ji.cloudfront.net/liveedge/eratv1/chunklist.m3u8");
        LiveEntity liveEntity46 = new LiveEntity("人间卫视", "http://61.216.177.73/sta/ch1764016.m3u8");
        LiveEntity liveEntity47 = new LiveEntity("TVBS新闻HD", "http://edge01p.friday.tw/live/fet005/chunklist.m3u");
        LiveEntity liveEntity48 = new LiveEntity("民视新闻HD", "http://edge01p.friday.tw/live/fet002/chunklist.m3u");
        LiveEntity liveEntity49 = new LiveEntity("纪实频道超清", "http://183.251.61.207/PLTV/88888888/224/3221225946/index.m3u8");
        LiveEntity liveEntity50 = new LiveEntity("全纪实超清", "http://27.148.240.200/PLTV/88888888/224/3221226096/index.m3u8");
        LiveEntity liveEntity51 = new LiveEntity("北京纪实超清", "http://183.251.61.207/PLTV/88888888/224/3221225944/index.m3u8");
        LiveEntity liveEntity52 = new LiveEntity("上海纪实超清", "http://183.251.61.207/PLTV/88888888/224/3221225946/index.m3u8CCTV-1HD");
        LiveEntity liveEntity53 = new LiveEntity("CCTV-1超清", "http://27.148.241.176/PLTV/88888888/224/3221226193/1.m3u8");
        LiveEntity liveEntity54 = new LiveEntity("CCTV-1超清", "http://183.252.176.10/PLTV/88888888/224/3221225922/index.m3u8");
        LiveEntity liveEntity55 = new LiveEntity("CCTV-1超清", "http://183.251.61.207/PLTV/88888888/224/3221225922/index.m3u8");
        LiveEntity liveEntity56 = new LiveEntity("CCTV-2超清", "http://183.252.176.10/PLTV/88888888/224/3221225923/index.m3u8");
        LiveEntity liveEntity57 = new LiveEntity("CCTV-2超清", "http://183.251.61.207/PLTV/88888888/224/3221225923/index.m3u8");
        LiveEntity liveEntity58 = new LiveEntity("CCTV-2标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-2/dujuejiami.m3u8");
        LiveEntity liveEntity59 = new LiveEntity("CCTV-3超清", "http://183.252.176.10/PLTV/88888888/224/3221225924/index.m3u8");
        LiveEntity liveEntity60 = new LiveEntity("CCTV-3超清", "http://183.251.61.207/PLTV/88888888/224/3221225924/index.m3u8");
        LiveEntity liveEntity61 = new LiveEntity("CCTV-3高清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-3/dujuejiami.m3u8");
        LiveEntity liveEntity62 = new LiveEntity("CCTV-4标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-4/dujuejiami.m3u8");
        LiveEntity liveEntity63 = new LiveEntity("CCTV-5超清", "http://183.252.176.10/PLTV/88888888/224/3221225925/index.m3u8");
        LiveEntity liveEntity64 = new LiveEntity("CCTV-5超清", "http://183.251.61.207/PLTV/88888888/224/3221225912/index.m3u8");
        LiveEntity liveEntity65 = new LiveEntity("CCTV-5超清", "http://183.251.61.207/PLTV/88888888/224/3221225925/index.m3u8");
        LiveEntity liveEntity66 = new LiveEntity("CCTV-5超清", "http://183.251.61.207/PLTV/88888888/224/3221225914/index.m3u8");
        LiveEntity liveEntity67 = new LiveEntity("CCTV-5超清", "http://27.148.240.205/PLTV/88888888/224/3221226068/1.m3u8");
        LiveEntity liveEntity68 = new LiveEntity("CCTV-5+超清", "http://183.252.176.10/PLTV/88888888/224/3221225939/index.m3u8");
        LiveEntity liveEntity69 = new LiveEntity("CCTV-5+超清", "http://183.251.61.207/PLTV/88888888/224/3221225939/index.m3u8");
        LiveEntity liveEntity70 = new LiveEntity("CCTV-5+超清", "http://183.251.61.207/PLTV/88888888/224/3221225915/index.m3u8");
        LiveEntity liveEntity71 = new LiveEntity("CCTV-6超清", "http://183.252.176.10/PLTV/88888888/224/3221225926/index.m3u8");
        LiveEntity liveEntity72 = new LiveEntity("CCTV-6超清", "http://183.251.61.207/PLTV/88888888/224/3221225926/index.m3u8");
        LiveEntity liveEntity73 = new LiveEntity("CCTV-6高清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-6/dujuejiami.m3u8");
        LiveEntity liveEntity74 = new LiveEntity("CCTV-7超清", "http://183.252.176.10/PLTV/88888888/224/3221225927/index.m3u8");
        LiveEntity liveEntity75 = new LiveEntity("CCTV-7超清", "http://183.251.61.207/PLTV/88888888/224/3221225927/index.m3u8");
        LiveEntity liveEntity76 = new LiveEntity("CCTV-7标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-7/dujuejiami.m3u8");
        LiveEntity liveEntity77 = new LiveEntity("CCTV-8超清", "http://183.251.61.207/PLTV/88888888/224/3221225928/index.m3u8");
        LiveEntity liveEntity78 = new LiveEntity("CCTV-8高清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-8/dujuejiami.m3u8");
        LiveEntity liveEntity79 = new LiveEntity("CCTV-9超清", "http://183.252.176.10/PLTV/88888888/224/3221225929/index.m3u8");
        LiveEntity liveEntity80 = new LiveEntity("CCTV-9超清", "http://183.251.61.207/PLTV/88888888/224/3221225929/index.m3u8");
        LiveEntity liveEntity81 = new LiveEntity("CCTV-9标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-news/dujuejiami.m3u8");
        LiveEntity liveEntity82 = new LiveEntity("CCTV-9英", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-9/dujuejiami.m3u8");
        LiveEntity liveEntity83 = new LiveEntity("CCTV-10超清", "http://183.252.176.10/PLTV/88888888/224/3221225931/index.m3u8");
        LiveEntity liveEntity84 = new LiveEntity("CCTV-10超清", "http://183.251.61.207/PLTV/88888888/224/3221225931/index.m3u8");
        LiveEntity liveEntity85 = new LiveEntity("CCTV-10标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-10/dujuejiami.m3u8");
        LiveEntity liveEntity86 = new LiveEntity("CCTV-11超清", "http://183.251.61.207/PLTV/88888888/224/3221225815/index.m3u8");
        LiveEntity liveEntity87 = new LiveEntity("CCTV-11标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-11/dujuejiami.m3u8");
        LiveEntity liveEntity88 = new LiveEntity("CCTV-12超清", "http://183.252.176.10/PLTV/88888888/224/3221225932/index.m3u8");
        LiveEntity liveEntity89 = new LiveEntity("CCTV-12超清", "http://183.251.61.207/PLTV/88888888/224/3221225932/index.m3u8");
        LiveEntity liveEntity90 = new LiveEntity("CCTV-12标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-12/dujuejiami.m3u8");
        LiveEntity liveEntity91 = new LiveEntity("CCTV-13超清", "http://183.251.61.207/PLTV/88888888/224/3221225911/index.m3u8");
        LiveEntity liveEntity92 = new LiveEntity("CCTV-13标清", "http://live.hcs.cmvideo.cn:8088/envivo_x/SD/cctv13/711/01.m3u8?msisdn=0496669525468&mdspid=&spid=699004&netType=1&sid=2202208864&pid=2028597139&timestamp=20171031130211&SecurityKey=20171031130211&resourceId=&resourceType=&Channel_ID=1004_10010001005&ProgramID=608807423&ParentNodeID=-99&client_ip=61.160.233.17&assertID=2202208864&mtv_session=3a5fefa7aee69c643c4ad53e638443e1&HlsSubType=1&HlsProfileId=1&nphaid=0&encrypt=0cfbf0aa51c17b2a4c0f6d4c501b542a");
        LiveEntity liveEntity93 = new LiveEntity("CCTV-13标清", "http://183.207.249.14/PLTV/3/224/3221225560/index.m3u8");
        LiveEntity liveEntity94 = new LiveEntity("CCTV-13标清", "http://223.110.245.171/PLTV/3/224/3221225560/index.m3u8");
        LiveEntity liveEntity95 = new LiveEntity("CCTV-13标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-13/dujuejiami.m3u8");
        LiveEntity liveEntity96 = new LiveEntity("CCTV-14超清", "http://183.251.61.207/PLTV/88888888/224/3221225933/index.m3u8 ");
        LiveEntity liveEntity97 = new LiveEntity("CCTV-14标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-14/dujuejiami.m3u8");
        LiveEntity liveEntity98 = new LiveEntity("CCTV-15标清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/cctv-15/dujuejiami.m3u8");
        LiveEntity liveEntity99 = new LiveEntity("CETV1中国教育", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/jiaoyutv/dujuejiami.m3u8");
        LiveEntity liveEntity100 = new LiveEntity("湖南卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225935/index.m3u8");
        LiveEntity liveEntity101 = new LiveEntity("湖南卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdhunanstv/dujuejiami.m3u8");
        LiveEntity liveEntity102 = new LiveEntity("北京卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225937/index.m3u8");
        LiveEntity liveEntity103 = new LiveEntity("北京卫视高清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdbeijingstv/dujuejiami.m3u8");
        LiveEntity liveEntity104 = new LiveEntity("深圳卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225938/index.m3u8");
        LiveEntity liveEntity105 = new LiveEntity("深圳卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdshenzhenstv/dujuejiami.m3u8");
        LiveEntity liveEntity106 = new LiveEntity("东方卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225936/index.m3u8");
        LiveEntity liveEntity107 = new LiveEntity("东方卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hddongfangstv/dujuejiami.m3u8");
        LiveEntity liveEntity108 = new LiveEntity("江苏卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225930/index.m3u8");
        LiveEntity liveEntity109 = new LiveEntity("江苏卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdjiangsustv/dujuejiami.m3u8");
        LiveEntity liveEntity110 = new LiveEntity("浙江卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225934/index.m3u8");
        LiveEntity liveEntity111 = new LiveEntity("浙江卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdzhejiangstv/dujuejiami.m3u8");
        LiveEntity liveEntity112 = new LiveEntity("广东卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225942/index.m3u8");
        LiveEntity liveEntity113 = new LiveEntity("广东卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdguangdongstv/dujuejiami.m3u8");
        LiveEntity liveEntity114 = new LiveEntity("天津卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225941/index.m3u8");
        LiveEntity liveEntity115 = new LiveEntity("天津卫视超清", "http://183.252.176.10/PLTV/88888888/224/3221225941/index.m3u8");
        LiveEntity liveEntity116 = new LiveEntity("天津卫视高清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdtianjinstv/dujuejiami.m3u8");
        LiveEntity liveEntity117 = new LiveEntity("山东卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225943/index.m3u8");
        LiveEntity liveEntity118 = new LiveEntity("山东卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdshandongstv/dujuejiami.m3u8");
        LiveEntity liveEntity119 = new LiveEntity("江西卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225834/index.m3u8");
        LiveEntity liveEntity120 = new LiveEntity("江西卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/jiangxistv/dujuejiami.m3u8");
        LiveEntity liveEntity121 = new LiveEntity("湖北卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225948/index.m3u8");
        LiveEntity liveEntity122 = new LiveEntity("湖北卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdhubeistv/dujuejiami.m3u8");
        LiveEntity liveEntity123 = new LiveEntity("黑龙江卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225940/index.m3u8");
        LiveEntity liveEntity124 = new LiveEntity("黑龙江卫视超清", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/hdheilongjiangstv/dujuejiami.m3u8");
        LiveEntity liveEntity125 = new LiveEntity("四川卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/sichuanstv/dujuejiami.m3u8");
        LiveEntity liveEntity126 = new LiveEntity("安徽卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225945/index.m3u8");
        LiveEntity liveEntity127 = new LiveEntity("辽宁卫视超清", "http://183.252.176.10/PLTV/88888888/224/3221225947/index.m3u8");
        LiveEntity liveEntity128 = new LiveEntity("河北卫視高清", "http://weblive.hebtv.com/live/hbws_bq/index.m3u8");
        LiveEntity liveEntity129 = new LiveEntity("重庆卫视超清", "http://183.251.61.207/PLTV/88888888/224/3221225949/index.m3u8");
        LiveEntity liveEntity130 = new LiveEntity("东南卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/dongnanstv/dujuejiami.m3u8");
        LiveEntity liveEntity131 = new LiveEntity("甘肃卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/gansustv/dujuejiami.m3u8");
        LiveEntity liveEntity132 = new LiveEntity("吉林卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/jilinstv/dujuejiami.m3u8");
        LiveEntity liveEntity133 = new LiveEntity("陕西卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/shanxi1stv/dujuejiami.m3u8");
        LiveEntity liveEntity134 = new LiveEntity("广西卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/guangxistv/dujuejiami.m3u8");
        LiveEntity liveEntity135 = new LiveEntity("贵州卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/guizhoustv/dujuejiami.m3u8");
        LiveEntity liveEntity136 = new LiveEntity("河南卫視", "http://zzcs4.ott.henancatv.com/mlive/172_30_100_77_/cdn_proxy/os_ott_henancatv_com/live/HWH/1.m3u8");
        LiveEntity liveEntity137 = new LiveEntity("内蒙古卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/neimenggustv/dujuejiami.m3u8");
        LiveEntity liveEntity138 = new LiveEntity("宁夏卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/ningxiastv/dujuejiami.m3u8");
        LiveEntity liveEntity139 = new LiveEntity("新疆卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/xinjiangstv/dujuejiami.m3u8");
        LiveEntity liveEntity140 = new LiveEntity("西藏卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/xizangstv/dujuejiami.m3u8");
        LiveEntity liveEntity141 = new LiveEntity("云南卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/yunnanstv/dujuejiami.m3u8");
        LiveEntity liveEntity142 = new LiveEntity("旅游卫视", "http://117.169.120.142:8080/wh7f454c46tw1875988299_1988371409/ysten-businessmobile/live/lvyoustv/dujuejiami.m3u8");
        liveList.add(liveEntity01);
        liveList.add(liveEntity02);
        liveList.add(liveEntity03);
        liveList.add(liveEntity04);
        liveList.add(liveEntity05);
        liveList.add(liveEntity06);
        liveList.add(liveEntity07);
        liveList.add(liveEntity08);
        liveList.add(liveEntity09);
        liveList.add(liveEntity10);
        liveList.add(liveEntity11);
        liveList.add(liveEntity12);
        liveList.add(liveEntity13);
        liveList.add(liveEntity14);
        liveList.add(liveEntity15);
        liveList.add(liveEntity16);
        liveList.add(liveEntity17);
        liveList.add(liveEntity18);
        liveList.add(liveEntity19);
        liveList.add(liveEntity20);
        liveList.add(liveEntity21);
        liveList.add(liveEntity22);
        liveList.add(liveEntity23);
        liveList.add(liveEntity24);
        liveList.add(liveEntity25);
        liveList.add(liveEntity26);
        liveList.add(liveEntity27);
        liveList.add(liveEntity28);
        liveList.add(liveEntity29);
        liveList.add(liveEntity30);
        liveList.add(liveEntity31);
        liveList.add(liveEntity32);
        liveList.add(liveEntity33);
        liveList.add(liveEntity34);
        liveList.add(liveEntity35);
        liveList.add(liveEntity36);
        liveList.add(liveEntity37);
        liveList.add(liveEntity38);
        liveList.add(liveEntity39);
        liveList.add(liveEntity40);
        liveList.add(liveEntity41);
        liveList.add(liveEntity42);
        liveList.add(liveEntity43);
        liveList.add(liveEntity44);
        liveList.add(liveEntity45);
        liveList.add(liveEntity46);
        liveList.add(liveEntity47);
        liveList.add(liveEntity48);
        liveList.add(liveEntity49);
        liveList.add(liveEntity50);
        liveList.add(liveEntity51);
        liveList.add(liveEntity52);
        liveList.add(liveEntity53);
        liveList.add(liveEntity54);
        liveList.add(liveEntity55);
        liveList.add(liveEntity56);
        liveList.add(liveEntity57);
        liveList.add(liveEntity58);
        liveList.add(liveEntity59);
        liveList.add(liveEntity60);
        liveList.add(liveEntity61);
        liveList.add(liveEntity62);
        liveList.add(liveEntity63);
        liveList.add(liveEntity64);
        liveList.add(liveEntity65);
        liveList.add(liveEntity66);
        liveList.add(liveEntity67);
        liveList.add(liveEntity68);
        liveList.add(liveEntity69);
        liveList.add(liveEntity70);
        liveList.add(liveEntity71);
        liveList.add(liveEntity72);
        liveList.add(liveEntity73);
        liveList.add(liveEntity74);
        liveList.add(liveEntity75);
        liveList.add(liveEntity76);
        liveList.add(liveEntity77);
        liveList.add(liveEntity78);
        liveList.add(liveEntity79);
        liveList.add(liveEntity80);
        liveList.add(liveEntity81);
        liveList.add(liveEntity82);
        liveList.add(liveEntity83);
        liveList.add(liveEntity84);
        liveList.add(liveEntity85);
        liveList.add(liveEntity86);
        liveList.add(liveEntity87);
        liveList.add(liveEntity88);
        liveList.add(liveEntity89);
        liveList.add(liveEntity90);
        liveList.add(liveEntity91);
        liveList.add(liveEntity92);
        liveList.add(liveEntity93);
        liveList.add(liveEntity94);
        liveList.add(liveEntity95);
        liveList.add(liveEntity96);
        liveList.add(liveEntity97);
        liveList.add(liveEntity98);
        liveList.add(liveEntity99);
        liveList.add(liveEntity100);
        liveList.add(liveEntity101);
        liveList.add(liveEntity102);
        liveList.add(liveEntity103);
        liveList.add(liveEntity104);
        liveList.add(liveEntity105);
        liveList.add(liveEntity106);
        liveList.add(liveEntity107);
        liveList.add(liveEntity108);
        liveList.add(liveEntity109);
        liveList.add(liveEntity110);
        liveList.add(liveEntity111);
        liveList.add(liveEntity112);
        liveList.add(liveEntity113);
        liveList.add(liveEntity114);
        liveList.add(liveEntity115);
        liveList.add(liveEntity116);
        liveList.add(liveEntity117);
        liveList.add(liveEntity118);
        liveList.add(liveEntity119);
        liveList.add(liveEntity120);
        liveList.add(liveEntity121);
        liveList.add(liveEntity122);
        liveList.add(liveEntity123);
        liveList.add(liveEntity124);
        liveList.add(liveEntity125);
        liveList.add(liveEntity126);
        liveList.add(liveEntity127);
        liveList.add(liveEntity128);
        liveList.add(liveEntity129);
        liveList.add(liveEntity130);
        liveList.add(liveEntity131);
        liveList.add(liveEntity132);
        liveList.add(liveEntity133);
        liveList.add(liveEntity134);
        liveList.add(liveEntity135);
        liveList.add(liveEntity136);
        liveList.add(liveEntity137);
        liveList.add(liveEntity138);
        liveList.add(liveEntity139);
        liveList.add(liveEntity140);
        liveList.add(liveEntity141);
        liveList.add(liveEntity142);
        mAdapter.setNewData(liveList);
    }


    class LiveAdapter extends BaseQuickAdapter<LiveEntity, BaseViewHolder> {

        public LiveAdapter() {
            super(R.layout.item_live_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, LiveEntity item) {
            helper.setText(R.id.tv_live, item.getName());
            helper.setText(R.id.tv_url, item.getUrl());
        }
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LiveListActivity.class);
        activity.startActivity(intent);
    }

}
