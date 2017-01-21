package dcheungaa.procal.Func;

/**
 * Created by Bryan on 1/21/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dcheungaa.procal.R;

/**
 * This is not Static, hence funcItemsList has FuncActivity.funcItemsList supplied as arg
 */
public class FuncAdapter extends RecyclerView.Adapter<FuncAdapter.FuncViewHolder> {

    private List<FuncItem> funcItemsList;

    public class FuncViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public FuncViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
        }
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
        FuncItem funcItem = funcItemsList.get(position);
        holder.title.setText(funcItem.getTitle());
        holder.description.setText(funcItem.getDescription());
        //funcItem.setProcalContent();
    }

    @Override
    public int getItemCount() {
        return funcItemsList.size();
    }
}
