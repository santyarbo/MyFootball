package es.santyarbo.myfootball.ui.leagues

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import es.santyarbo.myfootball.PermissionRequester
import es.santyarbo.myfootball.R
import es.santyarbo.myfootball.databinding.LeaguesFragmentBinding
import es.santyarbo.myfootball.ui.common.EventObserver
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class LeaguesFragment : Fragment() {

    private lateinit var binding: LeaguesFragmentBinding
    private lateinit var navController: NavController
    private val args: LeaguesFragmentArgs by navArgs()

    private lateinit var adapter: LeaguesAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    val viewModel: LeaguesViewModel by lifecycleScope.viewModel(this) {
        parametersOf(args.countryId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LeaguesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel.navigateToLeague.observe(viewLifecycleOwner, EventObserver { id ->
            val action = LeaguesFragmentDirections.actionLeaguesFragmentToTeamsFragment(id)
            navController.navigate(action)
        })

        viewModel.requestLocationPermission.observe(viewLifecycleOwner, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })

        adapter = LeaguesAdapter(viewModel::onLeagueClicked)
        with(binding) {
            recycler.adapter = adapter
            viewmodel = viewModel
            lifecycleOwner = this@LeaguesFragment
            toolbar.inflateMenu(R.menu.leagues_menu)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.countriesFragment -> {
                        val action = LeaguesFragmentDirections.actionLeaguesFragmentToCountriesFragment()
                        navController.navigate(action)
                    }
                }
                return@setOnMenuItemClickListener true
            }
            viewModel.onCoarsePermissionRequested()
        }
    }
}