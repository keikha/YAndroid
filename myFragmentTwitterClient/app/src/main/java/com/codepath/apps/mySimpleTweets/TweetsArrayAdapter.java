package com.codepath.apps.mySimpleTweets;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mySimpleTweets.activities.ProfileActivity;
import com.codepath.apps.mySimpleTweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by keikha on 2/7/15.
 */

// responsible the tweet objects and turn them into views to display in the List

public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    public TweetsArrayAdapter(Context context, List<Tweet> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
    }


    // apply the viewholder pattern for optimization

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1  get the tweet
        final Tweet tweet = getItem(position);
        // 2 find or inflate the template
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet , parent , false);
        }

        // 3 find the subview to fill with the data
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvUser);

        // 4 populate data into the subviews
        tvBody.setText(tweet.getBody());
        tvUserName.setText(tweet.getUser().getName());
        tvTime.setText(tweet.getCreatedAt());
        tvUser.setText("@"+tweet.getUser().getScreenName());

        ivProfile.setImageResource(android.R.color.transparent);

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfile);

        ivProfile.setTag(tweet.getUser().getScreenName());

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( getContext() , ProfileActivity.class);

                i.putExtra("screen_name" , tweet.getUser().getScreenName());

                getContext().startActivity(i);

            }
        });
        // 5 return the view to be inserted into the list
        return convertView;

    }
}
