package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAliasTextView;
    private TextView mAliasLabel;
    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredients;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //We look for the views that we are going to use in the layout
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAliasTextView = findViewById(R.id.also_known_tv);
        mAliasLabel = findViewById(R.id.also_known_label);
        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method populate the UI with the values obtained with the json parser.
     */
    private void populateUI() {
        mDescriptionTextView.setText(sandwich.getDescription());
        mOriginTextView.setText(sandwich.getPlaceOfOrigin());

        List<String> ingredients = sandwich.getIngredients();
        for (String ingredient:ingredients)
            mIngredients.append(ingredient + " ");

        List<String> alias = sandwich.getAlsoKnownAs();
        if (alias.size() != 0) {
            for (String name : alias)
                mAliasTextView.append(name + " ");

        } else {
            mAliasTextView.setVisibility(View.GONE);
            mAliasLabel.setVisibility(View.GONE);

        }

        String origin = sandwich.getPlaceOfOrigin();

        // If we dont have the data for the origin of the sandwich then
        // we print unknown.
        if (origin == null || origin.isEmpty())
            mOriginTextView.setText(R.string.unknown);
    }
}
