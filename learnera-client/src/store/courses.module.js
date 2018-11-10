import {courseService} from '../services'
import Vue from 'vue';

import crudModule from './crud.module'
const {state, actions: crudActions, mutations: crudMutations} = crudModule(courseService);

const getters = {
  coursesByTeacherId: (state) => (teacherId) => {
    return state.items.filter(i => i.teacherId === teacherId);
  }
}

const actions = {
  ...crudActions,
  getByTeacherId({commit}, id) {
    return new Promise((resolve, reject) => {
      commit('getByTeacherIdRequest', id);

      courseService.getByTeacherId(id)
        .then(courses => {
          commit('getByTeacherIdSuccess', {teacherId: id, courses});
          resolve(courses);
        })
        .catch(e => {
          commit('getByTeacherIdFailure', {id, error: e});
          reject(e);
        });
    });
  },
}

const mutations = {
  ...crudMutations,
  getByTeacherIdRequest(state, id) {
    console.log('Getting courses with teacher id: ', id)
  },
  getByTeacherIdSuccess(state, {teacherId, courses}) {
    console.log('Got courses: ', courses);
    courses.forEach(course => {
      const idx = state.items.findIndex(c => c.id === course.id);
      if (idx === -1) {
        state.items.push({...course, teacherId});
      } else {
        Vue.set(state.items, idx, {...course, teacherId});
      }
    });
  },
  getByTeacherIdFailure(state, {id, error}) {
    console.error('Failed to get item with id: ', id);
    console.error('Error: ', error);
  }
}

export const courses = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}