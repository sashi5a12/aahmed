var togglers = new Class({
	options: {
		togglers: [],
		toggleds: []
	},
	initialize: function(options) {
		this.setOptions(options);
		this.options.togglers.each(function(toggler,index) {
			toggler.addEvent('click', this.toggle.bindAsEventListener(this,index));
			if (toggler.hasClass('active')) {this.active=index} else {this.options.toggleds[index].addClass('hidden')}; 
		}, this);
	},
	toggle: function(event,index) {
		var event=new Event(event);
		
		this.options.togglers[this.active].removeClass('active');
		this.options.toggleds[this.active].addClass('hidden');
		this.options.togglers[index].addClass('active');
		this.options.toggleds[index].removeClass('hidden');
		this.active=index;

		event.preventDefault();
		return false;
	}
});

togglers.implement(new Options, new Events);

window.addEvent('domready', function(){
	if ($('togglers'))
	{			
		var el=$('togglers');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled')
		});
	}
	if ($('togglers_local'))
	{		
		var el=$('togglers_local');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_local')
		});
	}
	if ($('togglers_alt_lg'))
	{		
		var el=$('togglers_alt_lg');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_alt_lg')
		});
	}
	if ($('togglers_alt_sm'))
	{		
		var el=$('togglers_alt_sm');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_alt_sm')
		});
	}
	if ($('togglers_alt_lg'))
	{		
		var el=$('togglers_alt_lg');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_alt_lg')
		});
	}
	if ($('togglers_alt_lg_1'))
	{		
		var el=$('togglers_alt_lg_1');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_alt_lg_1')
		});
	}
	if ($('togglers_alt_lg_2'))
	{		
		var el=$('togglers_alt_lg_2');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_alt_lg_2')
		});
	}
	if ($('togglers_alt_lg_3'))
	{		
		var el=$('togglers_alt_lg_3');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_alt_lg_3')
		});
	}
	if ($('togglers_alt_lg_4'))
	{		
		var el=$('togglers_alt_lg_4');
		pattern_togglers = new togglers({
			togglers: $ES('a',el),
			toggleds: $ES('.toggled_alt_lg_4')
		});
	}
});