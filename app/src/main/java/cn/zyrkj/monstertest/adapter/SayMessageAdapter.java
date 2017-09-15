package cn.zyrkj.monstertest.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.zyrkj.monstertest.R;
import cn.zyrkj.monstertest.bean.SayMessage;

/**
 * sayMessage适配器
 */

public class SayMessageAdapter extends BaseAdapter{
    /**
     * Item类型,int值.必须从0开始依次递增.
     * */
    private static final int TYPE_MESSAGE = 0;
    private static final int TYPE_SWITCHABLE = 1;

    /**
     * Item Type 的数量
     * */
    private static final int TYPE_ITEM_COUNT = 2;

    /**
     * 数据
     * */
    private ArrayList<SayMessage> list = new ArrayList<>();
    private LayoutInflater mInflater;

    /**
     * 幻灯片
     * */
    // TODO: 2017/9/14  需写成动态获取数据
    private View switchable;    //幻灯片布局
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置  
    private int oldPosition = 0;
    //存放图片的id  
    private int[] imageIds = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };
    //存放图片的标题  
    private String[]  titles = new String[]{
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"
    };
    private TextView title;
    private ViewPagerAdapter adapter;

    private void initSwitchable() {
        mViewPaper = switchable.findViewById(R.id.vp);

        //显示的图片  
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(switchable.getContext());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点  
        dots = new ArrayList<View>();
        dots.add(switchable.findViewById(R.id.dot_0));
        dots.add(switchable.findViewById(R.id.dot_1));
        dots.add(switchable.findViewById(R.id.dot_2));
        dots.add(switchable.findViewById(R.id.dot_3));
        dots.add(switchable.findViewById(R.id.dot_4));

//        title = (TextView) findViewById(R.id.title);
        title = switchable.findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    /**
     * 自定义Adapter 
     *
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub  
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub  
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * end 幻灯片
     * */

    public SayMessageAdapter(Context context, ArrayList<SayMessage> messages) {
        this.list = messages;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据position获取Item的类型
     * */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_SWITCHABLE;
        }else {
            return TYPE_MESSAGE;
        }
    }

    /**
     * 返回Item Type的总数量
     * */
    @Override
    public int getViewTypeCount() {
        return TYPE_ITEM_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * 不同类型的ViewHolder
         * */
        MessageViewHolder messageViewHolder = null;   //sayMessage
        SwitchableViewHolder switchableViewHolder = null;   //幻灯片
        
        switch (getItemViewType(position)){
            case TYPE_MESSAGE:
                messageViewHolder = new MessageViewHolder();
                SayMessage message = list.get(position);
                if(convertView == null){
                    convertView = mInflater.inflate(R.layout.list_item, null);
                    messageViewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
                    //setTag()
                    convertView.setTag(messageViewHolder);
                }else{ //缓存复用
                    //setTag()
                    messageViewHolder = (MessageViewHolder) convertView.getTag();
                }
                messageViewHolder.userName.setText(message.getUser().getUserName());
                break;
            case TYPE_SWITCHABLE:
                switchableViewHolder = new SwitchableViewHolder();
                if(convertView == null){
                    convertView = mInflater.inflate(R.layout.switchable, null);
                    switchable = convertView;
                    initSwitchable();
                    //setTag()
                    convertView.setTag(switchableViewHolder);
                }else{ //缓存复用
                    //setTag()
                    switchableViewHolder = (SwitchableViewHolder) convertView.getTag();
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    class MessageViewHolder {
        protected ImageView userImage;
        protected TextView userName;

        public MessageViewHolder() {
            super();
        }

        public MessageViewHolder(ImageView userImage, TextView userName) {
            this.userImage = userImage;
            this.userName = userName;
        }
    }

    class SwitchableViewHolder {
        protected ViewPager mViewPaper;
        protected TextView title;
        protected ArrayList<View> dots;

        public SwitchableViewHolder() {
            super();
        }

        public SwitchableViewHolder(ViewPager mViewPaper, TextView title, ArrayList<View> dots) {
            this.mViewPaper = mViewPaper;
            this.title = title;
            this.dots = dots;
        }
    }


    public ViewPager getmViewPaper() {
        return mViewPaper;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public int[] getImageIds() {
        return imageIds;
    }
}
