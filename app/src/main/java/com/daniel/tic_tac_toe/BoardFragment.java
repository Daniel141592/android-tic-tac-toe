package com.daniel.tic_tac_toe;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class BoardFragment extends Fragment {
    private static final String TAG = "KOLKOKRZYZ";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GameViewModel viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        NavController navController = Navigation.findNavController(view);

        View.OnClickListener listener = v -> viewModel.update(Integer.parseInt(v.getTag().toString()));

        Button[] buttons = new Button[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = view.findViewWithTag(Integer.toString(i));
            buttons[i].setOnClickListener(listener);
        }


        TextView message = view.findViewById(R.id.textMessage);
        viewModel.getRoom().observe(getViewLifecycleOwner(), room -> {
            if (room == null || room.getTurn() == null || room.getTurn() == 2) {
                message.setText(R.string.waiting);
            } else {
                String str = room.getTurn() == 0 ? getString(R.string.krzyzyk) : getString(R.string.kolko);
                str += " ("+room.getNicks()[room.getTurn()]+")";
                message.setText(str);

                for (int i = 0; i < room.getB().length; i++) {
                    if (room.getB()[i] != null)
                        buttons[i].setText(room.getB()[i].toString());
                }
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error)
                Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_SHORT).show();
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navController.popBackStack(R.id.homeFragment, false);
            }
        });
    }
}
