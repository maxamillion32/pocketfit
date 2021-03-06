package team.monroe.org.pocketfit.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.monroe.team.android.box.app.ui.GenericListViewAdapter;
import org.monroe.team.android.box.app.ui.GetViewImplementation;

import team.monroe.org.pocketfit.FoodActivity;
import team.monroe.org.pocketfit.PocketFitApp;
import team.monroe.org.pocketfit.R;
import team.monroe.org.pocketfit.RoutinesActivity;
import team.monroe.org.pocketfit.presentations.Exercise;
import team.monroe.org.pocketfit.presentations.Product;

public class ProductEditorFragment extends BodyFragment<FoodActivity>{

    private String mProductId;
    private Product mProduct;

    @Override
    protected boolean isHeaderSecondary() {
        return true;
    }

    @Override
    protected String getHeaderName() {
        return "Edit Product";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_product;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mProductId = getStringArgument("product_id");
        if (mProductId == null) throw new IllegalStateException();

        application().function_getProduct(mProductId, observe_function(State.STOP, new PocketFitApp.DataAction<Product>() {
            @Override
            public void data(Product product) {
                mProduct = product;
                if (mProduct == null) mProduct = new Product(mProductId);
                view_text(R.id.edit_title).setText(ifNotNull(mProduct.title));
                view_text(R.id.edit_calories).setText(ifNotNull(mProduct.calories));
                view_text(R.id.edit_carbs).setText(ifNotNull(mProduct.carbs));
                view_text(R.id.edit_protein).setText(ifNotNull(mProduct.protein));
                view_text(R.id.edit_fats).setText(ifNotNull(mProduct.fats));
            }
        }));
    }

    private String ifNotNull(Object obj) {
        return obj != null? obj.toString():"";
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mProduct != null) {
            mProduct.title = view_text(R.id.edit_title).getText().toString();
            mProduct.calories = readPositiveInteger(R.id.edit_calories);
            mProduct.carbs = readPositiveFloat(R.id.edit_carbs);
            mProduct.fats = readPositiveFloat(R.id.edit_fats);
            mProduct.protein = readPositiveFloat(R.id.edit_protein);
            application().function_updateProduct(mProduct, observe_function(State.ANY, new PocketFitApp.DataAction<Void>() {
                @Override
                public void data(Void data) {

                }
            }));
        }
    }

    protected Integer readPositiveInteger(int r_text) {
        Integer value;
        String text = view_text(r_text).getText().toString();
        try {
            value = Math.abs(Integer.parseInt(text));
        }catch (Exception e){
            value = null;
        }
        return value;
    }

    protected Float readPositiveFloat(int r_text) {
        Float value;
        String text = view_text(r_text).getText().toString();
        try {
            value = Math.abs(Float.parseFloat(text));
        }catch (Exception e){
            value = null;
        }
        return value;
    }

    private void onItemRemove() {
        application().function_deleteProduct(mProduct.id, observe_function(State.STOP, new PocketFitApp.DataAction<Boolean>() {
            @Override
            public void data(Boolean data) {
                if (!data){
                    Toast.makeText(activity(), "Couldn`t remove product. Product currently in use.", Toast.LENGTH_LONG).show();
                } else {
                    mProduct = null;
                    owner().close_current();
                }
            }
        }));
    }

    @Override
    public View build_HeaderActionsView(ViewGroup actionPanel, LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.actions_remove, actionPanel, false);
        view.findViewById(R.id.action_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemRemove();
            }
        });
        return view;
    }


}
