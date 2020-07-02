package com.daniel.tic_tac_toe;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class JoinRoomFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GameViewModel viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.button_join_room).setOnClickListener(v -> {
            EditText editText = view.findViewById(R.id.editText_roomID);
            if (!editText.getText().toString().trim().equals("")) {
                viewModel.checkIfCanJoinRoom(Integer.parseInt(editText.getText().toString().trim()));
            }
        });

        viewModel.getRoom().observe(getViewLifecycleOwner(), room -> {
            if (room == null)
                return;
            if (room.isCanJoin()) {
                navController.navigate(R.id.action_joinRoomFragment_to_sendNickFragment);
            } else {
                Snackbar.make(view, R.string.room_full_or_doesnt_exists, Snackbar.LENGTH_LONG).show();
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
