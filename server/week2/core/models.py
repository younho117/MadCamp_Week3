from django.db import models

# Create your models here.
class TimeStampModel(models.Model):
    created = models.DateField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)
    class Meta:
        abstract = True