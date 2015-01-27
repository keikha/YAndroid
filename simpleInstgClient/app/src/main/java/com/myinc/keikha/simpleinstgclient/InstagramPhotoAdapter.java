package com.myinc.keikha.simpleinstgclient;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by keikha on 1/26/15.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {

   public InstagramPhotoAdapter(Context context , List<InstagramPhoto> photos)
   {
       super( context , R.layout.itemphoto , photos);
   }


    public static class ViewHolder
    {

        TextView caption;
        ImageView photo ;
        TextView username ;
        ImageView userPhoto;
        TextView likes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;

        // check if we using recycled view
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemphoto, parent, false);

            viewHolder.caption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.imgPhoto);
            viewHolder.username = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.userPhoto = (ImageView) convertView.findViewById(R.id.imgUser);
            viewHolder.likes = (TextView) convertView.findViewById(R.id.tvLikes);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.caption.setText(photo.caption);
        viewHolder.photo.getLayoutParams().height = photo.hight;
        viewHolder.username.setText(photo.username);
        viewHolder.likes.setText( photo.likes_count+"");


        // reset the image from the recycled view
        viewHolder.photo.setImageResource(0);
        viewHolder.userPhoto.setImageResource(0);
        // ask from the photos ro be added ro the imageview based on the photo url

        // background :
        Picasso.with(getContext()).load(photo.imageURL).into(viewHolder.photo);
        Picasso.with(getContext()).load(photo.profilePictureURL).into(viewHolder.userPhoto);

        return convertView;

    }


    // get an item in a position, convert it into a row in the list view
//    @Override
    public View getView_old(int position, View convertView, ViewGroup parent) {
        // take the date source at position

        // get the date

        InstagramPhoto photo = getItem(position);

        // check if we using recycled view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemphoto, parent, false);
        }
            TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
            TextView tvUser = (TextView) convertView.findViewById(R.id.tvUsername);
            ImageView imgUser = (ImageView) convertView.findViewById(R.id.imgUser);

            TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikes);

            tvCaption.setText(photo.caption);
            imgPhoto.getLayoutParams().height = photo.hight;
            tvUser.setText(photo.username);
            tvLikesCount.setText( photo.likes_count+"");


            // reset the image from the recycled view
            imgPhoto.setImageResource(0);
            imgUser.setImageResource(0);
            // ask from the photos ro be added ro the imageview based on the photo url

            // background :
            Picasso.with(getContext()).load(photo.imageURL).into(imgPhoto);
            Picasso.with(getContext()).load(photo.profilePictureURL).into(imgUser);

        return convertView;

        // lookup the subview within the templates

        //populate the subviews
    }
}
