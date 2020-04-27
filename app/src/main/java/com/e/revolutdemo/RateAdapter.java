package com.e.revolutdemo;

import android.app.Activity;
import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.revolutdemo.currency_data.Currency;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {
    private ArrayList<Currency> alCurrencies;
    private Context ctx;
    private IMoveItemCallback moveItemCallback;
    private HashMap<String, Double> map;

    // using a set to collect all visible view holders
    private Set<RateViewHolder> mBoundViewHolders = new HashSet<>();

    // set the base country to EUR and the rate to 1.0
    private String baseCountry = "EUR";
    private double baseValue = 1;

    // to check if the keyboard is shown
    private boolean isKeyboardShown = false;

    // to check if we're updating the data
    private boolean isUpdating = false;


    public RateAdapter(ArrayList<Currency> alCurrencies, HashMap<String, Double> map, Context ctx, IMoveItemCallback moveItemCallback) {
        this.alCurrencies = alCurrencies;
        this.map = map;
        this.ctx = ctx;
        this.moveItemCallback = moveItemCallback;

        // listening to the the keyboard event
        setKeyboardListener();
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate, parent, false);
        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RateViewHolder holder, final int position) {
        String name = alCurrencies.get(position).getName();
        holder.tvCur.setText(name);
        holder.tvCurDes.setText(alCurrencies.get(position).getDes());
        holder.etAmount.setText(getAmount(name));
        holder.ivFlag.setImageResource(alCurrencies.get(position).getFlag());
        setListener(holder);

        // add the view holder to the set when it's bound
        mBoundViewHolders.add(holder);
    }

    @Override
    public void onViewRecycled(@NonNull RateViewHolder holder) {
        super.onViewRecycled(holder);

        // remove the view holder if it's recycled
        mBoundViewHolders.remove(holder);
    }


    /**
     * baseValue : currentValue =  baseRate : currentRate
     * currentValue * baseRate = baseValue * currentRate
     * currentValue = baseValue * currentRate / baseRate
     * <p>
     * For example
     * <p>
     * baseValue = 10.0
     * baseRate = 5.0
     * currentRate = 2.0
     * <p>
     * 10.0 : currentValue = 1.5 : 2
     * currentValue = 10.0 * 2.0 / 5.0 = 4.0
     */
    private String getAmount(String name) {
        if (name.equals(baseCountry)) {
            return String.valueOf(baseValue);
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        double rate = map.get(name);
        double baseRate = map.get(baseCountry);
        return df.format(baseValue * rate / baseRate);
    }

    @Override
    public int getItemCount() {
        return alCurrencies.size();
    }


    private void setListener(final RateViewHolder holder) {
        View.OnClickListener onItemClicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapItem(holder);
                InputMethodManager imm = (InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE);
                // if imm is not null and the keyboard is shown, dismiss the keyboard
                if (isKeyboardShown && imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        };
        holder.itemView.setOnClickListener(onItemClicked);
    }


    // This listener would be automatically unregistered the event on the Activity's onDestroy
    private void setKeyboardListener() {
        KeyboardVisibilityEvent.setEventListener(
                (Activity) ctx,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        isKeyboardShown = isOpen;
                    }
                });
    }


    public void setMap(HashMap<String, Double> map) {
        // don't update the view if the keyboard is shown
        if (!isKeyboardShown) {
            this.map = map;
            // don't update the whole view since we only need to update the value of the edittext
            //notifyDataSetChanged();

            // update the edittexts for the visible view holders
            updateValue();
        }
    }

    // move item from its current position to the top
    private void swapItem(final RateViewHolder holder) {
        int fromPosition = holder.getAdapterPosition();
        Currency currency = alCurrencies.get(fromPosition);

        // use the top country as the base country after swapped
        baseValue = Double.valueOf(holder.etAmount.getText().toString());
        baseCountry = currency.getName();


        // remove and add the item
        alCurrencies.remove(fromPosition);
        alCurrencies.add(0, currency);
        notifyItemMoved(fromPosition, 0);

        // use this callback to notify the recycler view to scroll the view to the top
        moveItemCallback.itemMoved();
    }


    // Provide a reference to the views for each data item
    public class RateViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCur;
        private TextView tvCurDes;
        private EditText etAmount;
        private ImageView ivFlag;


        public RateViewHolder(View itemView) {
            super(itemView);
            tvCur = itemView.findViewById(R.id.tvCur);
            tvCurDes = itemView.findViewById(R.id.tvCurDes);
            etAmount = itemView.findViewById(R.id.etAmount);
            ivFlag = itemView.findViewById(R.id.ivFlag);

            etAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // do nothing when updating the view or the keyboard is not shown
                    if (isUpdating || !isKeyboardShown) {
                        return;
                    }
                    baseCountry = tvCur.getText().toString();
                    if (charSequence.toString().startsWith(".")) {
                        charSequence = "0" + charSequence;
                    }
                    baseValue = charSequence.toString().isEmpty() ? 0 : Double.valueOf(charSequence.toString());
                    updateValue();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }

    private void updateValue() {
        isUpdating = true;
        for (RateViewHolder holder : mBoundViewHolders) {
            if (holder.tvCur.getText().toString().equals(baseCountry)) {
                continue;
            }
            holder.etAmount.setText(getAmount(holder.tvCur.getText().toString()));
        }
        isUpdating = false;
    }

}
