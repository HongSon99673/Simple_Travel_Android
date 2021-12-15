package com.example.simpletravel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.R;
import com.example.simpletravel.adapter.RecentlyRearchAdapter.ViewHodler;
import com.example.simpletravel.model.Comment;
import com.example.simpletravel.model.ListTrip;
import com.example.simpletravel.model.Services;
import com.example.simpletravel.model.Temp.IdTrip;
import com.example.simpletravel.model.Temp.IdUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ListComment> {
    private List<Comment> comments;
    private JDBCControllers jdbcControllers;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private Boolean isChoose = true;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public ListComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_comment,
                parent, false);
        return new ListComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListComment holder, int position) {
        Comment comment = comments.get(position);
        if (comment == null) {
            return;
        }
        //set images avatar
        byte[] decodedString = Base64.decode(String.valueOf(comment.getImages()), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.circleImageView.setImageBitmap(decodedByte);
        //set User name
        holder.UserName.setText(comment.getUserName());
        //set Location
        holder.Location.setText(comment.getIntroduce());
        //set star
        if (comment.getEvaluate() == 1) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (comment.getEvaluate() == 2) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (comment.getEvaluate() == 3) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (comment.getEvaluate() == 4) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        if (comment.getEvaluate() == 5) {
            holder.Star1.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star2.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star3.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star4.setImageResource(R.drawable.outline_star_purple500_black_48);
            holder.Star5.setImageResource(R.drawable.outline_star_purple500_black_48);
        }
        //set contribute
        holder.Contribute.setText(String.valueOf(comment.getContribute() + " đóng góp"));
        //set title
        holder.Title.setText(comment.getTitle());
        //set Time
        holder.Time.setText(comment.getTime());
        //set Type
        holder.Type.setText(comment.getType());
        //set summary
        holder.Summary.setText(comment.getSummary());
        //set Like
        holder.Quantity.setText(String.valueOf(comment.getLike()));
        int n = 0;
        if (IdUsers.IdUser == comment.getIdUser()){
            n = 1;
            holder.Like.setChecked(true);
        } else {
            n = 2;
            holder.Like.setChecked(false);
        }
        switch (n)
        {
            case 1:
                holder.Like.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        int m = 0;
                        if (cb.isChecked()== false){
                            m = 1;
                        } else {
                            m = 2;
                        }
                        switch (m){
                            case 1:
                                int like = (comment.getLike()) - 1 ;
                                comment.setLike(like);//set variable like
                                holder.Quantity.setText(String.valueOf(like));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                            connection = jdbcControllers.ConnectionData();
                                            Log.e("Delete Like", "True");
                                            statement = connection.createStatement();
                                            String sql = "delete from Likes where IdUser = " + IdUsers.IdUser + " and IdRating = " +
                                                    "" + comment.getIdRating() + "";
                                            ResultSet resultSet = statement.executeQuery(sql);
                                            //close connect to data base
                                            resultSet.close();
                                            statement.close();
                                            connection.close();
                                        } catch (Exception ex) {
                                            Log.e("Delete Like", ex.getMessage());
                                        }
                                    }
                                }).start();
                                Toast.makeText(cb.getContext(),
                                        "Bạn xóa yêu thích",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                int like1 = (comment.getLike()) + 1;
                                comment.setLike(like1);//set variable like
                                holder.Quantity.setText(String.valueOf(like1));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                            connection = jdbcControllers.ConnectionData();
                                            Log.e("Add Like", "true");
                                            String sql = "insert into Likes(IdUser, IdRating) values ('" + IdUsers.IdUser +
                                                    "','" + comment.getIdRating() + "')";
                                            preparedStatement = connection
                                                    .prepareStatement(sql);
                                            preparedStatement.executeUpdate();
                                            preparedStatement.close();
                                            connection.close();
                                        } catch (Exception ex) {
                                            Log.e("Add Like", ex.toString());
                                        }
                                    }
                                }).start();
                                Toast.makeText(cb.getContext(),
                                        "Bạn đã thêm yêu thích.",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            case 2:
                holder.Like.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        int m = 0;
                        if (cb.isChecked()== true){
                            m = 1;
                        } else {
                            m = 2;
                        }
                        switch (m){
                            case 1:
                                int like = (comment.getLike()) + (1) ;
                                comment.setLike(like);//set variable like
                                holder.Quantity.setText(String.valueOf(like));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                            connection = jdbcControllers.ConnectionData();
                                            Log.e("Add Like", "true");
                                            String sql = "insert into Likes(IdUser, IdRating) values ('" + IdUsers.IdUser +
                                                    "','" + comment.getIdRating() + "')";
                                            preparedStatement = connection
                                                    .prepareStatement(sql);
                                            preparedStatement.executeUpdate();
                                            preparedStatement.close();
                                            connection.close();
                                        } catch (Exception ex) {
                                            Log.e("Add Like", ex.toString());
                                        }
                                    }
                                }).start();
                                Toast.makeText(cb.getContext(),
                                        "Bạn đã thêm yêu thích.",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                int like1 = (comment.getLike())-(1);
                                comment.setLike(like1);//set variable like
                                holder.Quantity.setText(String.valueOf(like1));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            jdbcControllers = new JDBCControllers(); //tao ket noi toi DB
                                            connection = jdbcControllers.ConnectionData();
                                            Log.e("Delete Like", "True");
                                            statement = connection.createStatement();
                                            String sql = "delete from Likes where IdUser = " + IdUsers.IdUser + " and IdRating = " +
                                                    "" + comment.getIdRating() + "";
                                            ResultSet resultSet = statement.executeQuery(sql);
                                            //close connect to data base
                                            resultSet.close();
                                            statement.close();
                                            connection.close();
                                        } catch (Exception ex) {
                                            Log.e("Delete Like", ex.getMessage());
                                        }
                                    }
                                }).start();
                                Toast.makeText(cb.getContext(),
                                        "Bạn đã xóa yêu thích.",
                                        Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                break;
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (comments != null) {
            return comments.size();
        }
        return 0;
    }

    public class ListComment extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private ImageView Star1, Star2, Star3, Star4, Star5;
        private TextView UserName, Contribute, Title, Time, Type, Summary, Quantity,
                TimeWrite, Location;
        public CheckBox Like;

        public ListComment(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.comment_img_Avatar);
            Star1 = itemView.findViewById(R.id.comment_star_1);
            Star2 = itemView.findViewById(R.id.comment_star_2);
            Star3 = itemView.findViewById(R.id.comment_star_3);
            Star4 = itemView.findViewById(R.id.comment_star_4);
            Star5 = itemView.findViewById(R.id.comment_star_5);
            UserName = itemView.findViewById(R.id.comment_txt_UserName);
            Contribute = itemView.findViewById(R.id.comment_txt_Contribute);
            Title = itemView.findViewById(R.id.comment_txt_Title);
            Type = itemView.findViewById(R.id.comment_txt_Type);
            Summary = itemView.findViewById(R.id.comment_txt_Summary);
            Time = itemView.findViewById(R.id.comment_txt_Time);
            Quantity = itemView.findViewById(R.id.comment_txt_Quantity_Like);
            TimeWrite = itemView.findViewById(R.id.comment_txt_DetailTime);
            Like = itemView.findViewById(R.id.comment_txt_Like);
            Location = itemView.findViewById(R.id.comment_txt_Location);
        }
    }

}
