<template>
  <item-list :items="mappedCourses"
             :actions="actions"
             api-url="/course" @actionClicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import ItemList from './ItemList.vue'

export default {
  name: 'UserCourseList',
  components: {ItemList},
  props: ['user', 'actions'],
  data() {
    return {
    };
  },
  computed: {
    ...mapGetters('courses', {coursesByTeacherId: 'coursesByTeacherId'}),
    ...mapState('account', {
      currentUser: state => state.user
    }),
    mappedCourses: function() {
      if (!this.user || !this.user.role) {
        return [];
      }
      let ret = [];
      if (this.user.role === 'TEACHER') {
        ret = this.coursesByTeacherId(this.user.id);
      } else if (this.user.role === 'STUDENT') {
        ret = [...new Set(this.user.studyGroups.map(g => {
          return g.courses.map(c => {
            return {...c, groups:[]}
          });
        }).flat())];
      }

      ret = ret.map(course => {
        return {
          id: course.id,
          name: course.name,
          description: course.description,
          avatar: course.info ? course.info.avatar : undefined,
        };
      })
      return ret;
    }
  },
  watch: {
    user: function(val) {
      if (val && val.id && val.role === 'TEACHER') {
        this.getCoursesByTeacherId(this.user.id);
      }
    }
  },
  created() {
    if (this.user.id && this.user.role === 'TEACHER') {
      this.getCoursesByTeacherId(this.user.id);
    }
  },
  methods: {
    ...mapActions('courses', {
      getCoursesByTeacherId: 'getByTeacherId'
    }),
    onActionClicked(info) {
      this.$emit('actionClicked', info);
    }
  }
}
</script>
