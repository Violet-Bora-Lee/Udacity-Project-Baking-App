package com.violetboralee.android.bakingappnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.violetboralee.android.bakingappnew.pojo.Step;

import java.util.List;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepFragment extends Fragment {
    private RecyclerView mStepsRecyclerView;
    private ShortDescriptionAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_a_recipe_list, container, false);

        mStepsRecyclerView = (RecyclerView) view.findViewById(R.id.steps_recycler_view);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        RecipeLab recipeLab = RecipeLab.get(getActivity());
        List<Step> steps = recipeLab.getSteps(1);

        mAdapter = new ShortDescriptionAdapter(steps);
        mStepsRecyclerView.setAdapter(mAdapter);
    }

    private class ShortDescriptionHolder extends RecyclerView.ViewHolder {
        public TextView mShortDescriptionTextView;

        public ShortDescriptionHolder(View itemView) {
            super(itemView);

            mShortDescriptionTextView =
                    (TextView) itemView.findViewById(R.id.list_item_short_description_text_view);
        }
    }

    private class ShortDescriptionAdapter extends RecyclerView.Adapter<ShortDescriptionHolder> {
        private List<Step> mSteps;

        public ShortDescriptionAdapter(List<Step> steps) {
            mSteps = steps;
        }

        @Override
        public ShortDescriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_item_step, parent, false);

            return new ShortDescriptionHolder(view);
        }

        @Override
        public void onBindViewHolder(ShortDescriptionHolder holder, int position) {
            Step step = mSteps.get(position);
            holder.mShortDescriptionTextView.setText(step.getShortDescription());
        }

        @Override
        public int getItemCount() {
            return mSteps.size();
        }
    }


}
