package com.example.piotr.zadaniowiec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.piotr.zadaniowiec.MainActivity.zadania;

public class DodajFragment extends DialogFragment {

    EditText mEditText;
    Button mButton;
    String zadanie;
    InputMethodManager manager;
    static String item;
    static String oldItem;

    static DodajFragment newInstance(String requestCode) {
        if (requestCode != null) {
            item = requestCode;
        } else {
            item = null;
        }
        DodajFragment f = new DodajFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog, container);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = view.findViewById(R.id.zadanie_edit);
        mButton = view.findViewById(R.id.dodaj_button);
        mButton.setEnabled(false);

        if (item != null) {
            mEditText.setText(item);
            mButton.setEnabled(true);
        }
        mEditText.requestFocus();

        manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(mEditText, 0);
        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEditText.getText().toString().length() > 0) {
                    mButton.setEnabled(true);
                } else {
                    mButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zadanie = mEditText.getText().toString();

                hideInput(v);

                setToastDependingOnItem();
                removeFromZadania();

                zadania.add(zadanie);
                Intent intent = new Intent(getContext(), MainActivity.class);
                //intent.putExtra("zadanie", zadanie);
                startActivity(intent);
            }
        });
    }

    private void hideInput(View v) {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void removeFromZadania() {
        if (item != null) {
            int i = 0;
            int inToRemove = 0;
            for (String itemInArray : zadania) {
                if (itemInArray.equals(item)) {
                    inToRemove = i;
                }
                i++;
            }
            zadania.remove(inToRemove);
        }
    }

    private void setToastDependingOnItem() {
        if (item == null) {
            Toast.makeText(getContext(), "Dodano zadanie", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Edytowano zadanie", Toast.LENGTH_LONG).show();

        }
    }
}
