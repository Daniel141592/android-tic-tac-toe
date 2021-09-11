package com.daniel.tic_tac_toe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class BoardFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GameViewModel viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        NavController navController = Navigation.findNavController(view);
        getActivity().setTitle(getString(R.string.room_id)+" "+viewModel.getRoomID());

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
                String str;
                if (room.isDraw())
                    str = getString(R.string.draw);
                else if (room.getWinner() != null) {
                    str = getString(R.string.wins) + " " + room.getNicks()[room.getWinner()];
                    message.setTextColor(room.getWinner().equals(room.getPlayerNumber()) ? Color.RED : Color.BLACK);
                } else {
                    str = room.getTurn() == 0 ? getString(R.string.krzyzyk) : getString(R.string.kolko);
                    str += " (" + room.getNicks()[room.getTurn()] + ")";
                    message.setTextColor(room.getTurn().equals(room.getPlayerNumber()) ? Color.RED : Color.BLACK);
                }
                message.setText(str);

                for (int i = 0; i < room.getBoard().length; i++) {
                    if (room.getBoard()[i] != null) {
                        buttons[i].setText(room.getBoard()[i] == 0 ? "×" : "⃝");
                        buttons[i].setTextColor(room.getBoard()[i].equals(room.getPlayerNumber()) ? Color.RED : Color.BLACK);
                    }
                }
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error)
                Snackbar.make(view, R.string.connection_error, Snackbar.LENGTH_INDEFINITE).show();
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //viewModel.stopRefreshing();
                navController.popBackStack(R.id.homeFragment, false);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_share);
        if(item != null)
            item.setVisible(true);
    }
}
