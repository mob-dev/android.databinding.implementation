import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class ExampleFragment extends Fragment {
    private FragmentExampleBinding fragmentExampleBinding;
    private ExampleViewModel exampleViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      /* Initiate binding component for xml */
      fragmentExampleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_example, container, false);
      /* Initiate the view model */
      exampleViewModel = ViewModelProviders.of(this).get(ExampleViewModel.class);
      
      /**
        * Sets the ExampleViewModel that should be used to set the data in xml
        */
      fragmentExampleBinding.setExampleViewModel(exampleViewModel);

      /**
        * Sets the {@link LifecycleOwner} that should be used for observing changes of
        * LiveData in this binding
        */
      fragmentExampleBinding.setLifecycleOwner(this);

      /* Get the root view from binding object */
      View view = fragmentExampleBinding.getRoot();
      return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exampleViewModel.init(this);
        setValueGettingViewIdFromBindingObject();
    }

    public void setValueGettingViewIdFromBindingObject() {
      fragmentExampleBinding.textView.setText("Example");
    }
}