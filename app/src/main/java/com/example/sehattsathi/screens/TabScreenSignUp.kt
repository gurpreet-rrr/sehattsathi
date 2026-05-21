package com.example.sehattsathi.screens



import android.R.attr.icon
import android.R.attr.text
import android.R.id.tabs
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sehattsathi.viewmodel.MyViewModel

import kotlinx.coroutines.launch


@Composable
fun tabScreenSignUp( navController: NavController, viewModel: MyViewModel

){

    val tabs = listOf(
        TabItem1("Patient", Icons.Default.Person, Icons.Filled.Person,),
        TabItem1("Admin", Icons.Default.LocalPharmacy, Icons.Filled.LocalPharmacy),
        TabItem1("Chemist", Icons.Default.LocalHospital, Icons.Filled.LocalHospital))



    // making page state
    val pagerState = rememberPagerState(pageCount = {tabs.size})

    // Custom coroutine scope
    val scope = rememberCoroutineScope()



    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),

        ){

        TabRow(selectedTabIndex = pagerState.currentPage,

            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color(0x0085C1FF),  // Your custom indicator color
                    height = 2.dp               // Adjust thickness here
                )
            }
        ) {


            tabs.forEachIndexed { index, tabItem->
                Tab (

                    modifier = Modifier.fillMaxWidth(),
                    // defining criteria for tab being selected
                    selected = pagerState.currentPage == index,
                    // slected color
                    selectedContentColor = Color(0xFF005986),
                    // unselected color
                    unselectedContentColor = Color(0xFF949494),
                    onClick = {

                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }


                    },
                    text = {
                        Row(
                            verticalAlignment = CenterVertically,
                        ){
                            Icon(
                                if(pagerState.currentPage == index){
                                    tabItem.fillicon

                                }
                                else{
                                    tabItem.icon
                                },
                                contentDescription = ""
                            )

                            Text(text = tabItem.title)
                        }
                    },





                    )
            }
        }

        HorizontalPager( state = pagerState){
            val MyViewModel = null
            when(it){
                0-> signupScreenPatient(navController = navController, viewModel = viewModel)
                1-> signupScreenAdmin(navController = navController, viewModel = viewModel )
                2-> signupScreenChemist()
            }
        }

    }




}



data class  TabItem1(
    val title : String,
    val icon : ImageVector,
    val fillicon : ImageVector
)