package com.example.jhonsson.practicacamara;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class GalleryAdapter extends BaseAdapter
{
	Context context;
    protected File [] imagenes;
    Bitmap bitmap;

	//guardamos las imágenes reescaladas para mejorar el rendimiento ya que estas operaciones son costosas
	//se usa SparseArray siguiendo la recomendación de Android Lint
	static SparseArray<Bitmap> imagenesEscaladas = new SparseArray<Bitmap>(7);

	public GalleryAdapter(Context context, File [] imagenes)
	{
		super();
		this.imagenes = imagenes;
		this.context = context;
	}

	@Override
	public int getCount()
	{
		return imagenes.length;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Holder holder = null;
		
            if (convertView == null)
            {
                holder = new Holder();
                LayoutInflater ltInflate = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                convertView = ltInflate.inflate(R.layout.gallery_items, null);

                holder.setTextView((TextView) convertView.findViewById(R.id.txtNombreFoto));
                holder.setImage((ImageView) convertView.findViewById(R.id.imagenCuentas));

                convertView.setTag(holder);
             }
            else
            {
                holder = (Holder) convertView.getTag();
            }
		
		 if (imagenesEscaladas.get(position) == null)
		 {

             //Bitmap bMap = BitmapFactory.decodeFile(String.valueOf(imagenes[position]));
             ////estas son
          bitmap = BitmapUtils.decodeSampledBitmapFromResource(imagenes[position],250, 0);
			//imagenesEscaladas.put(position, bitmap);
              //bMap = BitmapFactory.decodeFile(imagenes[position].getPath());
		 }

		//holder.getImage().setImageBitmap(imagenesEscaladas.get(position));
        holder.getImage().setImageBitmap(bitmap);
        //holder.getImage().setLayoutParams(new GridView.LayoutParams(250,250));
        String[] nombre = imagenes[position].getPath().split("/");
		holder.getTextView().setText( nombre[nombre.length-1]+ "");
		 
		return convertView;		
	}
	
	class Holder
	{

        ImageView image;
		TextView textView;
		
		public ImageView getImage() 
		{
			return image;
		}
		
		public void setImage(ImageView image) 
		{
			this.image = image;
		}
		
		public TextView getTextView() 
		{
			return textView;
		}
		
		public void setTextView(TextView textView) 
		{
			this.textView = textView;
		}		
		
	}

}