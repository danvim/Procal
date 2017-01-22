package dcheungaa.procal.Func;

/**
 * Created by Bryan on 1/21/2017.
 */

import android.app.Activity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dcheungaa.procal.InputHandler;
import dcheungaa.procal.R;

import static dcheungaa.procal.Func.FuncActivity.context;

/**
 * This is not Static, hence funcItemsList has FuncActivity.funcItemsList supplied as arg
 */
public class FuncAdapter extends RecyclerView.Adapter<FuncAdapter.FuncViewHolder> {

    public List<FuncItem> funcItemsList;

    public class FuncViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description;
        public RelativeLayout funcItemLayout;
        public ImageView funcItemMenu;

        public FuncViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.description = (TextView) view.findViewById(R.id.description);
            this.funcItemLayout = (RelativeLayout) view.findViewById(R.id.func_item_layout);
            this.funcItemMenu = (ImageView) view.findViewById(R.id.menu_button);
        }
    }

    private FuncItemClickListener clickListener;

    public interface FuncItemClickListener {
        void onClick(View view);
    }

    public FuncAdapter(List<FuncItem> funcItemsList) {
        this.funcItemsList = funcItemsList;
    }

    @Override
    public FuncViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.func_item, parent, false);
        return new FuncViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FuncViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return funcItemsList.size();
    }
}