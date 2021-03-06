# Red Flag

![Keyword_Filtering](/images/filter_keywords.svg)

A proof of concept of a red-flagging framework (not to be taken seriously).

All outbound communications (emails only - for now) will be scanned for certain
keywords - which will trigger editorial review of the offending communications.

[This could be extended into a compliance framework with a little effort.
For instance, all outgoing communications could be archived (for compliance
purposes). This archive should of course be searchable. And any offending
communications should be quarantined - and of course NOT be archived.]

## Rationale

Certain discussions are incompatible with a productive workplace.

Likewise, social media (whatever its benefits) is probably also a distraction.

## Simplifications

Keyword scanning is the simplest possible way of carrying out this type of
processing: other more elaborate methods are possible but will be ignored
for the purposes of this spike.

It is possible to imagine many forms of outbound communications, but this
project will limit itself to simple emails (ignoring attachments). As the
main effort is the framework, the emails to be scanned will be obtained
from static pools.

Persistence, logging, statistics and reporting will be largely ignored. It
is possible to imagine a persistence framework that would allow for post-hoc
analysis but that will not be addressed here. Logging and reporting, to the
extent that they are present at all, will be present here as console logging.

Likewise, editorial review of offending communications will not be addressed.

## Keywords

These will be configurable and relatively arbitrary, consisting of `Facebook`,
`GLaDOS` (sorry Portal fans), `Trump`, `Twitter` and various semantic forms of
the word `tweet` (the water-cooler is the place for this type of discussion).

## Chaos Monkeys

In order to ensure failover and recovery (modelling an unreliable distributed
communications network), one in five of the keyword scans will randomly (and
silently) fail.

While the intent is to use unreliable off-peak processing (to keep costs down)
there needs to be some escalation mechanism to ensure processing takes place in
a timely fashion. Likewise there need to be mechanisms to handle any hardware or
network failure.

## Requirements

Requires __make__ and __Java 1.8__ to be installed (but could be backported to
an earlier version of Java).

Also, __Java 1.9__ (with FLOW) has been released, but many projects - such as
[Guava](https://github.com/google/guava) and [Scala](http://www.scala-lang.org/) -
still seem to rely on Java 1.8. And there are probably better asynchronous
solutions available ([Akka](https://akka.io/)? [RxJava](https://github.com/ReactiveX/RxJava)?).

#### To build

    make build

#### To run

    make run

#### To clean

    make clean

## Privacy & Compliance

For competitive reasons, most firms may well favour an on-premises solution to a
cloud-based one. And this bears consideration for legislative reasons as well, as
many countries have data residency requirements. Where data access needs to be
audited, this may well prove to be another consideration to take into account.

## To Do

This is largely a list of elements that are out of scope of the current exercise.

As such, these are features that remain to be implemented.

- [ ] Implement Email streaming
- [x] Implement Email body scanning
- [ ] Implement Email attachment scanning
- [ ] Investigate the performance impact of parallel streams
- [ ] Other forms of communications scanning (possibly machine learning)
- [ ] Statistical analysis of offending communications
- [ ] Persistence (format to be determined but probably some type of document store)
- [ ] Restart of scanning daemon whenever keyword list is changed
- [ ] Plumbing and triggers for (streaming) email feed
- [ ] Plumbing and triggers for document feed
- [ ] Plumbing and triggers for PDF feed
- [ ] Plumbing and triggers for WORD feed
- [ ] Plumbing and triggers for editorial review of offending communications
- [ ] Upgrade to HTTP/2 when it is stable in Java
