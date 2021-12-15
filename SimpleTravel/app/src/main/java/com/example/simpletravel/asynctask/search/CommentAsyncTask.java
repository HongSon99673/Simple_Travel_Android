package com.example.simpletravel.asynctask.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletravel.JDBC.JDBCControllers;
import com.example.simpletravel.adapter.CommentAdapter;
import com.example.simpletravel.model.Comment;
import com.example.simpletravel.model.Temp.IdServices;
import com.example.simpletravel.model.Temp.IdUsers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommentAsyncTask extends AsyncTask<Object, Void, ArrayList<Comment>> {
    private List<Comment> comments;
    private Context context;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private ProgressDialog progressDialog;
    private Connection connection;
    private JDBCControllers jdbcControllers;
    private Statement statement;

    public CommentAsyncTask(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Vui lòng chờ");
        progressDialog.setMessage("Đang tải dữ liệu ....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected ArrayList<Comment> doInBackground(Object... objects) {
        try {
            comments = new ArrayList<>();
            jdbcControllers = new JDBCControllers();
            connection = jdbcControllers.ConnectionData();
            Log.e("Comment", "True");
            statement = connection.createStatement();
            //list comment have like
            String comment = "select R.IdRating,(select L.IdUser from Likes L, Ratings S where L.IdRating = S.IdRating and S.IdService ='"+IdServices.IdService+"' and L.IdUser = '"+ IdUsers.IdUser +"'  and S.IdRating = R.IdRating  group by L.IdUser) as IdUser,\n" +
                    "U.Name, U.Address, U.Avatar, COUNT (R.IdUser ) as Contribute, AVG(R.Ratings) as Evaluate, \n" +
                    "R.Title, R.Time, R.Type, R.Summary, COUNT (L.IdRating) as Likes\n" +
                    "from Users as U, Ratings as R, Likes as L\n" +
                    "where U.IdUser = R.IdUser and R.IdRating = L.IdRating and R.IdService = '"+IdServices.IdService+"'\n" +
                    "group by R.IdRating, U.Name, U.Address, U.Avatar, R.Title, R.Time, R.Type, R.Summary";
            ResultSet rs = statement.executeQuery(comment);
            while (rs.next()) {
                comments.add(new Comment(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),
                        rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11), rs.getInt(12)));
            }
            //list comment not like

            //close connect
            rs.close();
            statement.close();
            connection.close();

            return (ArrayList<Comment>) comments;//return list comment
        } catch (Exception ex){
            Log.e("Comment", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Comment> comments) {
        super.onPostExecute(comments);
        if (comments != null){
            progressDialog.dismiss();
            commentAdapter = new CommentAdapter(comments);
            recyclerView.setAdapter(commentAdapter);
        }
    }
}
