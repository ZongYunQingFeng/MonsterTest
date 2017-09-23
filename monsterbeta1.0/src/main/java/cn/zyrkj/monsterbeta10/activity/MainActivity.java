package cn.zyrkj.monsterbeta10.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.zyrkj.monsterbeta10.R;
import cn.zyrkj.monsterbeta10.adapter.SayMessageAdapter;
import cn.zyrkj.monsterbeta10.bean.Customer;
import cn.zyrkj.monsterbeta10.bean.Product;
import cn.zyrkj.monsterbeta10.bean.SayMessage;
import cn.zyrkj.monsterbeta10.model.NineGridTestModel;
import cn.zyrkj.monsterbeta10.util.LoginStateApplication;
import cn.zyrkj.monsterbeta10.util.Util;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Context mContext;
    private Intent intent;
    //tab
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2, view3, view4;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private LayoutInflater mInflater;

    // 下拉刷新
    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private ImageView imageView;
    private SayMessageAdapter mAdapter;
    private ArrayList<SayMessage> sayMessageList;
    private ArrayList<Product> productlist;

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    // 幻灯片
    private ScheduledExecutorService scheduledExecutorService;

    //九宫格图片展示
    private List<NineGridTestModel> mList = new ArrayList<>();
    private String[] mUrls = new String[]{"http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3445377427,2645691367&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2644422079,4250545639&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1444023808,3753293381&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        //底部导航
        initTabLayout();
        //九宫格图片
        initListData();
        //下拉刷新
        initSwipeRefreshLayout();
        //个人设置界面
        initView();
        //自定义标题栏
        setCustomActionBar(0);
        //检测登录是否过期
        checksCache();
        app.setIsLogin(true);
    }
    
    
    //自定义标题栏
    private void initView() {
        ListView lv = (ListView) view4.findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_text, new String[] { "遥控", "收藏",
                "客服", "设置" }));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                intent = new Intent();
                switch (position) {
                    case 3:
                        intent.setClass(mContext,SettingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //设置标题栏
    private void setCustomActionBar(int position) {
        ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);

        TextView textView = (TextView) mActionBarView.findViewById(R.id.actionBarTitle);
        textView.setText(mTitleList.get(position));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);

        
        
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    /**
     * tab
     * */
    //初始化底部菜单栏
    private void initTabLayout() {
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mInflater = LayoutInflater.from(this);

        view1 = mInflater.inflate(R.layout.play_view, null);
        view2 = mInflater.inflate(R.layout.say_view, null);
        view3 = mInflater.inflate(R.layout.friend_view, null);
        view4 = mInflater.inflate(R.layout.self_view, null);
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        //添加页卡标题
        mTitleList.add(mContext.getString(R.string.play_btn));
        mTitleList.add(mContext.getString(R.string.say_btn));
        mTitleList.add(mContext.getString(R.string.friend_btn));
        mTitleList.add(mContext.getString(R.string.self_btn));
        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createView(R.drawable.selector_bg,mTitleList.get(0))));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createView(R.drawable.selector_bg_attach,mTitleList.get(1))));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createView(R.drawable.selector_bg_message,mTitleList.get(2))));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createView(R.drawable.selector_bg_info,mTitleList.get(3))));
        
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            /**
             * 选中tab后触发
             * @param tab 选中的tab
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //与pager 关联
                mViewPager.setCurrentItem(tab.getPosition(),true);
            }

            /**
             * 退出选中状态时触发
             * @param tab 退出选中的tab
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            /**
             * 重复选择时触发
             * @param tab 被 选择的tab
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。  
            //arg0 == 1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。  
            //当页面开始滑动的时候，三种状态的变化顺序为（1，2，0）  
            public void onPageScrollStateChanged(int arg0) {
            }
            //此方法里有3个参数</span></span>  
            //当你滑动时一直调用这个方法直到停止滑到  
            //arg0：表示现在的页面； arg1：表示当前页面偏移百分比； arg2：表示当前页面偏移的像素；  
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            //此方法里的 arg0 是表示显示的第几页，当滑到第N页，就会调用此方法，arg0=N；  
            public void onPageSelected(int arg0) {
                //关联 TabLayout
                mTabLayout.getTabAt(arg0).select();
                setCustomActionBar(arg0);
                switch (arg0) {
                    case 1:
                        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                        scheduledExecutorService.scheduleWithFixedDelay(
                                new ViewPageTask(),
                                4,
                                4,
                                TimeUnit.SECONDS);
                        break;
                    default:
                        if(scheduledExecutorService != null){
                            scheduledExecutorService.shutdown();
                            scheduledExecutorService = null;
                        }
                        break;
                }
            }

        });
    }
    private View createView(int icon, String tab){
        View view = getLayoutInflater().inflate(R.layout.fragment_tab_discover,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView title = (TextView) view.findViewById(R.id.title);
        imageView.setImageResource(icon);
        title.setText(tab);
        return  view;
    }

    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法，判断是否同一view
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }

    /**
     * end tab
     * */
    
    /**
     * 图片轮播任务 
     *
     */
    public class ViewPageTask implements Runnable{

        @Override
        public void run() {
            mAdapter.setCurrentItem((mAdapter.getCurrentItem() + 1) % mAdapter.getImageIds().length);
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据 
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mAdapter.getmViewPaper().setCurrentItem(mAdapter.getCurrentItem());
        };
    };

    /*
    * 九宫格图片展示
    * */
    private void initListData() {
        NineGridTestModel model1 = new NineGridTestModel();
        model1.urlList.add(mUrls[0]);
        mList.add(model1);

        NineGridTestModel model2 = new NineGridTestModel();
        model2.urlList.add(mUrls[4]);
        mList.add(model2);

        NineGridTestModel model4 = new NineGridTestModel();
        for (String url : mUrls) {
            model4.urlList.add(url);
        }
        for (int i = 0; i < mUrls.length; i++) {
        }
        model4.isShowAll = false;
        mList.add(model4);

        NineGridTestModel model5 = new NineGridTestModel();
        for (String url : mUrls) {
            model5.urlList.add(url);
        }
        model5.isShowAll = true;//显示全部图片
        mList.add(model5);

        NineGridTestModel model6 = new NineGridTestModel();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(mUrls[i]);
        }
        mList.add(model6);

        NineGridTestModel model7 = new NineGridTestModel();
        for (int i = 3; i < 7; i++) {
            model7.urlList.add(mUrls[i]);
        }
        mList.add(model7);

        NineGridTestModel model8 = new NineGridTestModel();
        for (int i = 3; i < 6; i++) {
            model8.urlList.add(mUrls[i]);
        }
        mList.add(model8);
    }

    /*
    * end 九宫格图片展示
    * */


    /**
     * 按2次退出应用
     * */
    private Handler exitHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Util.t(getApplicationContext(), "再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            exitHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
    /**
     * end 按2次退出应用
     * */

    /**
     * 下拉刷新
     * */
    private void initSwipeRefreshLayout() {
        mSwipeLayout = view2.findViewById(R.id.id_swipe_ly);
        mListView = view2.findViewById(R.id.id_listview);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        sayMessageList = new ArrayList<>();
        sayMessageList.add(null);
        ArrayList<Product> productlist = new ArrayList<>();
        SayMessage message1 = new SayMessage();
        message1.setCustomer(new Customer("纵横天下", "", productlist));
        message1.setNineGridTestModel(mList.get(0));
        SayMessage message2 = new SayMessage();
        message2.setCustomer(new Customer("云淡风轻", "", productlist));
        message2.setNineGridTestModel(mList.get(2));
        SayMessage message3 = new SayMessage();
        message3.setCustomer(new Customer("清风微徐", "", productlist));
        message3.setNineGridTestModel(mList.get(3));
        SayMessage message4 = new SayMessage();
        message4.setCustomer(new Customer("徐徐扶风", "", productlist));
        message4.setNineGridTestModel(mList.get(4));
        SayMessage message5 = new SayMessage();
        message5.setCustomer(new Customer("风卷雷击", "", productlist));
        message5.setNineGridTestModel(mList.get(5));
        sayMessageList.add(message5);
        sayMessageList.add(message4);
        sayMessageList.add(message3);
        sayMessageList.add(message2);
        sayMessageList.add(message1);
        mAdapter = new SayMessageAdapter(this, sayMessageList);
//        mAdapter.setmList(mList);
        mListView.setAdapter(mAdapter);
    }

    private Handler refreshHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    productlist = new ArrayList<Product>();
                    SayMessage newMessage = new SayMessage();
                    newMessage.setCustomer(new Customer("横贯八方", "", productlist));
                    newMessage.setNineGridTestModel(mList.get(6));
                    sayMessageList.add(1,newMessage);
                    mAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    
    public void onRefresh()
    {
        // Log.e("xxx", Thread.currentThread().getName());  
        // UI Thread  

        refreshHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

    }
    /**
     * end 下拉刷新
     * */
}
