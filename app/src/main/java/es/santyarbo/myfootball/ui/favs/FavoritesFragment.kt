package es.santyarbo.myfootball.ui.favs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import es.santyarbo.myfootball.databinding.FavoritesFragmentBinding
import es.santyarbo.myfootball.ui.common.EventObserver
import es.santyarbo.myfootball.ui.teams.list.TeamsAdapter
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel  by lifecycleScope.viewModel(this)
    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var navController: NavController
    private lateinit var adapter: TeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver{
            navController.navigate(FavoritesFragmentDirections.actionFavoritesFragmentToTeamDetailFragment(it.id, -1))
        })

        adapter = TeamsAdapter(viewModel::onTeamClicked)
        with(binding) {
            recycler.adapter = adapter
            viewmodel = viewModel
            lifecycleOwner = this@FavoritesFragment
            viewModel.getTeamsFavorites()
        }
    }
}