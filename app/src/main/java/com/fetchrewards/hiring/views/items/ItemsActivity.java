package com.fetchrewards.hiring.views.items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fetchrewards.hiring.R;
import com.fetchrewards.hiring.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsActivity extends AppCompatActivity {
    ItemsViewModel itemsViewModel;
    private Spinner spinner;
    private RecyclerView itemsRecyclerView;
    RecyclerViewAdapterItems recyclerViewAdapterItems;
    private TextView emptyStateText;
    private String selectedSpinnerItem = "";

    private List<Item> itemsListIdOne;
    private List<Item> itemsListIdTwo;
    private List<Item> itemsListIdThree;
    private List<Item> itemsListIdFour;

    private final static String TAG = "ItemsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        //initialize view model
        itemsViewModel = new ViewModelProvider(this).get(ItemsViewModel.class);
        itemsViewModel.setUpNetworkRequest();

        //initialize views
        spinner = findViewById(R.id.spinner);
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        emptyStateText = findViewById(R.id.emptyListText);

        //
        itemsListIdOne = new ArrayList<>();
        itemsListIdTwo = new ArrayList<>();
        itemsListIdThree = new ArrayList<>();
        itemsListIdFour = new ArrayList<>();

        //fetch items
        itemsViewModel.fetchItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                //shows all items without grouping
                setUpRecyclerView(items);

                //group by list ids
                itemsListIdOne = items.stream().filter(item -> item.getListId() == 1).collect(Collectors.toList());
                itemsListIdTwo = items.stream().filter(item -> item.getListId() == 2).collect(Collectors.toList());
                itemsListIdThree = items.stream().filter(item -> item.getListId() == 3).collect(Collectors.toList());
                itemsListIdFour = items.stream().filter(item -> item.getListId() == 4).collect(Collectors.toList());
            }
        });

        spinnerSelection();
    }

    private void spinnerSelection() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerItem = parent.getItemAtPosition(position).toString();
                Log.i(TAG, selectedSpinnerItem);

                switch (selectedSpinnerItem) {
                    case "1":
                        //sort by listId then name
                        itemsListIdOne.sort(new Item());
                        setUpRecyclerView(itemsListIdOne);

                        Log.i(TAG, itemsListIdOne.size()+"::count of items with a list id of 1");
                        break;
                    case "2":
                        itemsListIdTwo.sort(new Item());
                        setUpRecyclerView(itemsListIdTwo);

                        Log.i(TAG, itemsListIdTwo.size()+"::count of items with a list id of 2");
                        break;
                    case "3":
                        itemsListIdThree.sort(new Item());
                        setUpRecyclerView(itemsListIdThree);

                        Log.i(TAG, itemsListIdThree.size()+"::count of items with a list id of 3");
                        break;
                    case "4":
                        itemsListIdFour.sort(new Item());
                        setUpRecyclerView(itemsListIdFour);

                        Log.i(TAG, itemsListIdFour.size()+"::count of items with a list id of 4");
                        break;
                    default:
                        Log.i(TAG, "");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "Nothing has been selected");
            }
        });

    }

    private void setUpRecyclerView(List<Item> items) {
        //sort by listId then name
        items.sort(new Item());

        //filter out any items where "name" is blank or null.
        items.removeIf(item -> item.getName() == null || item.getName().equals(""));

        recyclerViewAdapterItems = new RecyclerViewAdapterItems(items);

        if (recyclerViewAdapterItems.getItemCount() > 0) {
            emptyStateText.setVisibility(View.INVISIBLE);
        } else {
            emptyStateText.setVisibility(View.VISIBLE);
        }

        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsRecyclerView.setAdapter(recyclerViewAdapterItems);

        recyclerViewAdapterItems.setOnItemClickListener(new RecyclerViewAdapterItems.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Item item = items.get(position);

                notification(item.getName());
            }
        });

        recyclerViewAdapterItems.notifyDataSetChanged();

    }

    private void notification(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}