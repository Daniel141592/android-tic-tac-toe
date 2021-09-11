package com.daniel.tic_tac_toe;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        GameViewModel viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        viewModel.setConnectionStatus(ConnectionStatus.NOT_CONNECTED);
        viewModel.getStartGameRequest().setValue(null);
        getActivity().setTitle(R.string.app_name);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.button_new_room).setOnClickListener(v -> {
            viewModel.setConnectionStatus(ConnectionStatus.CREATING_ROOM);
            navController.navigate(R.id.action_homeFragment_to_sendNickFragment);
        });

        view.findViewById(R.id.button_join_room).setOnClickListener(v -> {
            viewModel.setConnectionStatus(ConnectionStatus.CONNECTING);
            navController.navigate(R.id.action_homeFragment_to_joinRoomFragment);
        });
    }
}
