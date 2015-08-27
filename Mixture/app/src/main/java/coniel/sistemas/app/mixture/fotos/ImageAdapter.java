package coniel.sistemas.app.mixture.fotos;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.Vector;

import coniel.sistemas.app.mixture.R;

/**
 * Created by Jhonsson on 08/11/2014.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<Bitmap> images = new Vector<Bitmap>();
    int nGallery;

    public ImageAdapter(Context c) {
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
        nGallery = attr.getResourceId(R.styleable.HelloGallery_android_galleryItemBackground,1);
        attr.recycle();
    }

    public void AddImage(Bitmap b)
    {
        images.add(b);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int arg0) {
        return images.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        if(convertView ==null)
        {
            img = new ImageView(mContext);
        }
        else
        {
            img = (ImageView)convertView;
        }


        img.setImageBitmap(images.get(position));
        img.setLayoutParams(new Gallery.LayoutParams(350, 250));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setBackgroundResource(nGallery);
        return img;
    }

}
