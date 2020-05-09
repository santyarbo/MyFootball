package es.santyarbo.myfootball.ui.countries

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import es.santyarbo.myfootball.databinding.CountriesFragmentBinding
import es.santyarbo.myfootball.ui.common.EventObserver
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class CountriesFragment : Fragment() {

    private val viewModel: CountriesViewModel by lifecycleScope.viewModel(this)
    private lateinit var binding: CountriesFragmentBinding
    private lateinit var navController: NavController

    private lateinit var adapter: CountriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CountriesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigateToCountry.observe(viewLifecycleOwner, EventObserver { id ->
            val action = CountriesFragmentDirections.actionCountriesFragmentToLeaguesFragment(id)
            navController.navigate(action)
        })

        viewModel.navigateOnBack.observe(viewLifecycleOwner, EventObserver {
            view?.let {
                Navigation.findNavController(it).popBackStack()
            }
        })

        adapter = CountriesAdapter(viewModel::onCountryClicked)
        with(binding) {
            recycler.adapter = adapter
            viewmodel = viewModel
            lifecycleOwner = this@CountriesFragment
        }

    }

}
