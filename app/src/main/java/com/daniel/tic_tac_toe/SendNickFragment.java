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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SendNickFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_nick, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GameViewModel viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        TextView textView = view.findViewById(R.id.textView);
        if (viewModel.getConnectionStatus() == ConnectionStatus.CONNECTING)
            textView.setText(R.string.join_room_text);

        Button sendButton = view.findViewById(R.id.button_send_nick);
        EditText editText = view.findViewById(R.id.editText_nick);
        sendButton.setOnClickListener(v -> {
            String str = editText.getText().toString().trim();
            if (!str.equals("")) {
                if (viewModel.getConnectionStatus() == ConnectionStatus.CREATING_ROOM) {
                    viewModel.create(str);
                } else if (viewModel.getConnectionStatus() == ConnectionStatus.CONNECTING) {
                    viewModel.joinRoom(str);
                }
            } else {
                Snackbar.make(view, R.string.empty_edittext, Snackbar.LENGTH_SHORT).show();
            }
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendButton.performClick();
                return true;
            }
            return false;
        });

        NavController navController = Navigation.findNavController(view);
        viewModel.getStartGameRequest().observe(getViewLifecycleOwner(), startGameRequest -> {
            if (startGameRequest == null)
                return;
            if (startGameRequest.isCanPlay()) {
                if (startGameRequest.getRoomID() != null)
                    viewModel.setRoomID(startGameRequest.getRoomID());
                navController.navigate(R.id.action_sendNickFragment_to_boardFragment);
                viewModel.setConnectionStatus(ConnectionStatus.CONNECTED);
            } else if (!startGameRequest.isCanJoin()) {
                if (viewModel.getConnectionStatus() == ConnectionStatus.CONNECTING) {
                    Snackbar.make(view, R.string.room_full_or_doesnt_exists, Snackbar.LENGTH_LONG).show();
                    navController.popBackStack(R.id.joinRoomFragment, false);
                } else {
                    Snackbar.make(view, R.string.creating_room_error, Snackbar.LENGTH_LONG).show();
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
