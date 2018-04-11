package com.and.blf.baking_app.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.recycler_view.RecipeAdapter;
import com.and.blf.baking_app.utils.RecipeRetrofitService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterListFragment.RecipeClickListener} interface
 * to handle interaction events.
 */
public class MasterListFragment extends Fragment implements RecipeItemClickListener {
    private RecipeClickListener mRecipeClickListener;

    private static final String TAG = "RecyclerViewFragment";

    private RecipeRetrofitService mRecipeRetrofitService;

    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mGridLayoutManager;

    protected RecipeAdapter mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(),new WeakReference<RecipeItemClickListener>(this));

    public MasterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipeRetrofitService = RecipeRetrofitService.utils.getRecipeRetrofitService();

        initDataSet();
    }

    private void initDataSet() {
        if(! RecipeRetrofitService.utils.networkIsAvailable(getContext())){
            //set visibility of the indicator
            //show toast "net is not available"
            return;
        }

        Call<Recipe[]> recipesArrayCall = mRecipeRetrofitService.getAllRecipes();

        recipesArrayCall.enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(@NonNull Call<Recipe[]> call, @NonNull Response<Recipe[]> response) {
                try {
                    mRecipeAdapter.setRecipeList(response.body());
                }catch (NullPointerException e){
                    Log.d(getString(R.string.getingAllRecipesExceptionTag), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull  Call<Recipe[]> call, @NonNull Throwable t) {
                Log.d(getString(R.string.getingAllRecipesExceptionTag), t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.rv_main_list);
        mGridLayoutManager = new GridLayoutManager(getActivity(),getColumnCount());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mRecipeAdapter);

        return rootView;
    }

    private int getColumnCount() {
        //TODO implement counting amount of columns
        return 1;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeClickListener) {
            mRecipeClickListener = (RecipeClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
    * when recipe item is clicked in mRecyclerView
    * */
    @Override
    public void onRecipeItemClicked(int position) {
        Toast.makeText(getActivity(),String.valueOf(position),Toast.LENGTH_SHORT).show();
        //TODO: implement getting the recipe and passing it to mainActivity for detail fragment
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface RecipeClickListener {
        // TODO: Update argument type and name
        void onRecipeClicked(Parcelable recipe);
    }

}
