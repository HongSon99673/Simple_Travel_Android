package com.example.simpletravel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.simpletravel.R;
import com.example.simpletravel.model.Comment;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    //create variable
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.item_listview_comment, null);
        } else viewProduct = view;

        //Bind sữ liệu phần tử vào View
        Comment comment = (Comment) getItem(i);
        ((TextView) viewProduct.findViewById(R.id.comment_txt_UserName)).setText(comment.getUserName());
        ((TextView) viewProduct.findViewById(R.id.comment_txt_Contribute)).setText(String.valueOf(comment.getContribute() + " đóng góp"));
        ((TextView) viewProduct.findViewById(R.id.comment_txt_Rating)).setText(String.valueOf(comment.getIdRating()));
        ((TextView) viewProduct.findViewById(R.id.comment_txt_Title)).setText(comment.getTitle());
        ((TextView) viewProduct.findViewById(R.id.comment_txt_Time)).setText(comment.getTime());
        ((TextView) viewProduct.findViewById(R.id.comment_txt_Type)).setText(comment.getType());
        ((TextView) viewProduct.findViewById(R.id.comment_txt_Summary)).setText(comment.getSummary());
        ((TextView) viewProduct.findViewById(R.id.comment_txt_DetailTime)).setText("Đã viết vào" + comment.getTime());

        return viewProduct;
    }
}
