<template id="dashboard">
  <div>
    <v-toolbar color="white">
      <v-toolbar-title>Life Cube</v-toolbar-title>
    </v-toolbar>

    <v-card v-if="!gridViewSwitch">
      <v-card-title>
        <v-text-field
            v-model="searchInput"
            prepend-inner-icon="search"
            label="Search"
            @input="searchSubmissions"
            single-line
            hide-details
        ></v-text-field>
      </v-card-title>

      <v-data-table :headers="submissionViewHeaders"
                    :items="submissions"
                    :options.sync="listViewPagination"
                    :loading="submissionsAreLoading"
                    :footer-props="footerProps"
                    :server-items-length="totalElements"
                    @update:items-per-page="searchSubmissions"
                    class="elevation-1">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.id }}</td>
          <td>{{ props.item.text }}</td>
          <td>{{ props.item.type }}</td>
        </template>
      </v-data-table>
    </v-card>

    <v-card v-if="gridViewSwitch">

      <v-card v-if="gridViewSwitch">
        <v-card-title>
          <v-text-field
              v-model="searchInput"
              prepend-inner-icon="search"
              label="Search"
              @input="searchSubmissions"
              single-line
              hide-details
          ></v-text-field>
        </v-card-title>
        <v-container fluid grid-list-md>
          <v-layout row wrap>
            <v-flex v-for="submission in submissions" xs4 :key="submission.id">
              <v-card>
                <span class="headline white--text"></span>

                <v-card-title primary-title>
                  <div>
                    <h3 v-text="submission.type"></h3>
                  </div>
                </v-card-title>

                <v-card-text>
                  <div>
                    <h3>{{ submission.text | truncate(30) }}</h3>
                  </div>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
        <div class="text-xs-center">
          <v-pagination
              v-model="gridViewPage"
              :total-visible="7"
              :length=totalPages
              @input="onPageChange">
          </v-pagination>
        </div>
      </v-card>
    </v-card>

    <div>
      <v-btn medium color="primary" dark :to="{path: '/submission/add'}">Add New Submission</v-btn>
      <v-switch v-model="gridViewSwitch"
                :label="`${gridViewSwitch? 'Grid View': 'List View'}`"
                @change="didToggleGridView">
      </v-switch>
    </div>
  </div>
</template>

<script>
import api from './submission-backend-api'

export default {
  data() {
    return {
      gridViewSwitch: this.initialGridViewShowingState(),
      submissions: [],
      gridViewPage: 1,
      searchInput: '',
      totalElements: 0,
      totalPages: 1,
      submissionsAreLoading: false,
      listViewPagination: {
        page: 1,
        sortBy: ['id'],
        sortDesc: [false],
        itemsPerPage: this.initialRowsPerPage()
      },
      footerProps: {'items-per-page-options': [5, 10, 15, 100]},
      submissionViewHeaders: [
        {text: 'id', value: 'id'},
        {text: 'Text', value: 'text'},
        {text: 'Type', value: 'type'},
      ],
    }
  },
  watch: {
    listViewPagination: {
      handler() {
        localStorage.setItem('listViewRowsPerPage',
            this.listViewPagination.itemsPerPage.toString());
        this.searchSubmissions()
      },
      deep: true
    }
  },
  methods: {
    initialRowsPerPage() {
      let listViewRowsPerPage = localStorage.getItem('listViewRowsPerPage');
      if (listViewRowsPerPage) {
        return parseInt(listViewRowsPerPage);
      }
      return 10;
    },
    initialGridViewShowingState() {
      let gridViewShowing = localStorage.getItem('gridViewShowing');
      return !(gridViewShowing && 'false' === gridViewShowing);
    },
    didToggleGridView() {
      localStorage.setItem('gridViewShowing', this.gridViewSwitch.toString());
      this.searchSubmissions();
    },
    searchSubmissions() {
      this.submissionsAreLoading = true;
      let filter = {text: this.searchInput};
      if (this.gridViewSwitch) {
        filter.page = this.gridViewPage - 1;
        filter.size = 6;
      } else {
        filter.page = this.listViewPagination.page - 1;
        filter.sort = encodeURIComponent(this.listViewPagination.sortBy);
        filter.descending = this.listViewPagination.sortDesc[0]
        filter.size = this.listViewPagination.itemsPerPage;
      }
      return api.searchSubmissions(filter).then(response => {
        this.submissionsAreLoading = false;
        this.submissions = response.data.content;
        this.totalElements = response.data.totalElements;
        this.totalPages = response.data.totalPages;
      });
    },
    onPageChange(page) {
      this.gridViewPage = page;
      this.searchSubmissions();
    }
  },
  mounted() {
    this.searchSubmissions();
  },
}
</script>
